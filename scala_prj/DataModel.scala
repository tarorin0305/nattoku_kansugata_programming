opaque type Location = String
  object Location {
    def apply(location: String): Location = location
    extension (location: Location) {
      def name: String = location
    }
  }
opaque type Genre = String
  object Genre {
    def apply(genre: String): Genre = genre
    extension (genre: Genre) {
      def name: String = genre
    }
  }
opaque type YearActiveStart = Int
  object YearActiveStart {
    def apply(yearActiveStart: Int): YearActiveStart = yearActiveStart
    extension (yearActiveStart: YearActiveStart) {
      def year: Int = yearActiveStart
    }
  }
opaque type YearActiveEnd = Int
  object YearActiveEnd {
    def apply(yearActiveEnd: Int): YearActiveEnd = yearActiveEnd
    extension (yearActiveEnd: YearActiveEnd) {
      def year: Int = yearActiveEnd
    }
  }
object DataModel {

  case class User(
    name: String,
    city: Option[String],
    favoriteArtists: List[String]
  )

  def noCityOrMelbourne(users: List[User]): List[User] = {
    // users.filter(user => user.city.isEmpty || user.city == Some("Melbourne"))
    users.filter(user => user.city.forall(city => city == "Melbourne"))
  }
  def cityIsLagos(users: List[User]): List[User] = {
    users.filter(user => user.city == Some("Lagos"))
  }
  def favoriteIsBeeGees(users: List[User]): List[User] = {
    users.filter(user => user.favoriteArtists.contains("Bee Gees"))
  }
  def cityInitialIsT(users: List[User]): List[User] = {
    users.filter(user => user.city.exists(_.startsWith("T")))
  }
  def noOrOverEightLengthNameArtist(users: List[User]): List[User] = {
    // users.filter(user => user.favoriteArtists.exists(artist => artist.length >= 8) || user.favoriteArtists.isEmpty)
    users.filter(user => user.favoriteArtists.forall(artist => artist.length > 8))
  }
  def artistInitialIsM(users: List[User]): List[User] = {
    users.filter(user => user.favoriteArtists.exists(_.startsWith("M")))
  }

  case class Artist(
    name: String,
    genre: MusicGenre,
    origin: Location,
    yearsActive: YearsActive
  )
  enum MusicGenre {
    case HeavyMetal
    case HardRock
    case Pop
    case Rock
    case Funk
    case House
  }
  enum YearsActive {
    case StillActive(since: Int)
    case ActiveBetween(start: Int, end: Int)
  }
  case class PeriodInYears(
    start: Int,
    end: Option[Int]
  )

  def activeLength(artist: Artist, currentYear: Int): Int = {
    // if artist is still active, calculate currentYear - startYear
    // if artist is not active now, calculate endYear - startYear
    artist.yearsActive match {
      case YearsActive.StillActive(since) => currentYear - since
      case YearsActive.ActiveBetween(start, end) => end - start
    }
  }

  val artists = List(
    Artist("Metallica", MusicGenre.HeavyMetal, Location("U.S."), YearsActive.StillActive(1981)),
    Artist("Led Zeppelin", MusicGenre.HardRock, Location("England"), YearsActive.ActiveBetween(1968, 1980)),
    Artist("Bee Gees", MusicGenre.Pop, Location("England"), YearsActive.ActiveBetween(1958, 2003)),
    Artist("The Beatles", MusicGenre.Rock, Location("England"), YearsActive.ActiveBetween(1960, 1970)),
  )

  val users = List(
    User("Alice", Some("Melbourne"), List("Bee Gees")), User("Bob", Some("Lagos"), List("Bee Gees")), User("Eve", Some("Tokyo"), List.empty),
    User("Mallory", None, List("Metallica", "Bee Gees")), User("Trent", Some("Buenos Aires"), List("Led Zeppelin"))
  )

  def searchArtists(
    artists: List[Artist],
    genres: List[String],
    locations: List[String],
    searchByActiveYears: Boolean,
    activeAfter: Int,
    activeBefore: Int
  ): List[Artist] = {
    artists.filter(artist =>
      (genres.isEmpty || genres.contains(artist.genre)) &&
      (locations.isEmpty || locations.contains(artist.origin.name)) &&
      (!searchByActiveYears ||  wasArtistActive(artist, activeAfter, activeBefore)))
  }

  def wasArtistActive(
    artist: Artist,
    yearStart: Int,
    yearEnd: Int
  ): Boolean = {
    artist.yearsActive match {
      case YearsActive.StillActive(since) => since <= yearEnd
      case YearsActive.ActiveBetween(start, end) => start <= yearEnd && end >= yearStart
    }
  }

  // define Playlist class
  case class Playlist(
    name: String,
    listType: PlaylistType, // enum
    songs: List[Song]
  )

  // define PlaylistType enum
  enum PlaylistType {
    case UserGenerated
    case SpecificArtist(artist: Artist)
    case SpecificGenre(genre: List[PlaylistGenre])
  }

  enum PlaylistGenre {
    case Rock
    // case Pop
    // case HeavyMetal
    case Funk
    case House
  }

  // define Song class
  case class Song(
    artist: Artist,
    title: String,
  )

  // define PlaylistUser class
  case class PlaylistUser(
    name: String
  )

  val fooFightersPlaylist: Playlist = Playlist(
    "This is Foo Fighters",
    PlaylistType.SpecificArtist(Artist("Foo Fighters", MusicGenre.Rock, Location("U.S."), YearsActive.StillActive(1994))),
    List(
      // Song(Artist("Foo Fighters", MusicGenre.Rock, Location("U.S."), YearsActive.StillActive(1994)), "Everlong"),
      // Song(Artist("Foo Fighters", MusicGenre.Rock, Location("U.S."), YearsActive.StillActive(1994)), "The Pretender"),
      Song(Artist("Foo Fighters", MusicGenre.Rock, Location("U.S."), YearsActive.StillActive(1994)), "Learn to Fly"),
      Song(Artist("Foo Fighters", MusicGenre.Rock, Location("U.S."), YearsActive.StillActive(1994)), "Breakout"),
      // Song(Artist("Foo Fighters", MusicGenre.Rock, Location("U.S."), YearsActive.StillActive(1994)), "Best of You"),
      // Song(Artist("Foo Fighters", MusicGenre.Rock, Location("U.S."), YearsActive.StillActive(1994)), "My Hero"),
    )
  )
  val deepFocusPlaylist: Playlist = Playlist(
    "Deep Focus",
    PlaylistType.SpecificGenre(List(PlaylistGenre.Funk, PlaylistGenre.House)),
    List(
      Song(Artist("Daft Punk", MusicGenre.House, Location("France"), YearsActive.ActiveBetween(1993, 2021)), "One More Time"),
      Song(Artist("Chemical Brothers", MusicGenre.House, Location("U.S."), YearsActive.StillActive(1993)), "Hey Boy Hey Girl"),
    )
  )

  def gatherSongs(playlists: List[Playlist], artist: Artist, genre: MusicGenre): List[Song] = {
    // playlists の要素を順番に見ていき、引数に渡ってきた要素と同じプロパティを持っているかチェック
    // 同じプロパティがある場合はそのplaylistのみを保持する新しいList[playlist]を返す
    // その新しいListに対して、songsをmapで取り出す
    val newPlayLists: List[Playlist] = playlists.filter(playlist => playlist.listType match {
      case PlaylistType.UserGenerated => false
      case PlaylistType.SpecificArtist(artist) => playlist.songs.exists(_.artist == artist)
      case PlaylistType.SpecificGenre(genre) => playlist.songs.exists(_.artist.genre == genre)
    })
    newPlayLists.flatMap(_.songs)
  }

  def main(args: Array[String]): Unit = {
    println(activeLength(Artist("Metallica", MusicGenre.HeavyMetal, Location("U.S."), YearsActive.StillActive(1981)), 2022))
    println(activeLength(Artist("Led Zeppelin", MusicGenre.HardRock, Location("England"), YearsActive.ActiveBetween(1968, 1980)), 2022))
    println(activeLength(Artist("Bee Gees", MusicGenre.Pop, Location("England"), YearsActive.ActiveBetween(1958, 2003)), 2022))
    println("------------------")
    println(gatherSongs(List(fooFightersPlaylist, deepFocusPlaylist), Artist("Foo Fighters", MusicGenre.Rock, Location("U.S."), YearsActive.StillActive(1994)), MusicGenre.House))
  }
}
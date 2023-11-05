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
    yearsActive: PeriodInYears
  )
  enum MusicGenre {
    case HeavyMetal
    case HardRock
    case Pop
    case Rock
  }
  enum YearsActive {
    case StillActive(since: Int)
    case ActiveBetween(start: Int, end: Int)
  }
  case class PeriodInYears(
    start: Int,
    end: Option[Int]
  )

  val artists = List(
    Artist("Metallica", MusicGenre.HeavyMetal, Location("U.S."), PeriodInYears(1981, None)),
    Artist("Led Zeppelin", MusicGenre.HardRock, Location("England"), PeriodInYears(1968, Some(1980))),
    Artist("Bee Gees", MusicGenre.Pop, Location("England"), PeriodInYears(1958, Some(2003))),
    Artist("The Beatles", MusicGenre.Rock, Location("England"), PeriodInYears(1960, Some(1970))),
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
      (genres.isEmpty || genres.contains(artist.genre.name)) &&
      (locations.isEmpty || locations.contains(artist.origin.name)) &&
      (!searchByActiveYears || (
      (artist.yearsActive.end.forall(_ >= activeAfter)) &&
      (artist.yearsActive.start <= activeBefore))))
  }

  def main(args: Array[String]): Unit = {
    println(searchArtists(artists, List("Pop"), List("England"), true, 1950, 2022))
    println(searchArtists(artists, List.empty, List("England"), true, 1950, 2022))
    println(searchArtists(artists, List.empty, List.empty, true, 1950, 1979))
    println("----------------------------")
    println(noCityOrMelbourne(users))
    println(cityIsLagos(users))
    println(favoriteIsBeeGees(users))
    println(cityInitialIsT(users))
    println(noOrOverEightLengthNameArtist(users))
    println(artistInitialIsM(users))
  }
}
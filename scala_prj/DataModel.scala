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

  case class Artist(
    name: String,
    genre: Genre,
    origin: Location,
    yearsActiveStart: YearActiveStart,
    yearsActiveEnd: Option[YearActiveEnd]
  )
  case class User(
    name: String,
    city: Option[String],
    favoriteArtists: List[String]
  )

  val artists = List(
    Artist("Metallica", Genre("Heavy Metal"), Location("U.S."), YearActiveStart(1981), Some(YearActiveEnd(0))),
    Artist("Led Zeppelin", Genre("Hard Rock"), Location("England"), YearActiveStart(1968), Some(YearActiveEnd(1980))),
    Artist("Bee Gees", Genre("Pop"), Location("England"), YearActiveStart(1958), Some(YearActiveEnd(2003))),
    Artist("The Beatles", Genre("Rock"), Location("England"), YearActiveStart(1960), Some(YearActiveEnd(1970))),
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
      (artist.yearsActiveEnd.forall(_.year >= activeAfter)) &&
      (artist.yearsActiveStart.year <= activeBefore))))
  }

  def main(args: Array[String]): Unit = {
    println(searchArtists(artists, List("Pop"), List("England"), true, 1950, 2022))
    println(searchArtists(artists, List.empty, List("England"), true, 1950, 2022))
    println(searchArtists(artists, List.empty, List.empty, true, 1950, 1979))
  }
}
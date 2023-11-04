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
    isActive: Boolean,
    yearsActiveEnd: YearActiveEnd
  )
  val artists = List(
    Artist("Metallica", Genre("Heavy Metal"), Location("U.S."), YearActiveStart(1981), true, YearActiveEnd(0)),
    Artist("Led Zeppelin", Genre("Hard Rock"), Location("England"), YearActiveStart(1968), false, YearActiveEnd(1980)),
    Artist("Bee Gees", Genre("Pop"), Location("England"), YearActiveStart(1958), false, YearActiveEnd(2003)),
    Artist("The Beatles", Genre("Rock"), Location("England"), YearActiveStart(1960), false, YearActiveEnd(1970)),
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
      (genres.isEmpty || genres.contains(artist.genre.name)) && (locations.isEmpty || locations.contains(artist.origin.name)) && (!searchByActiveYears || ((artist.isActive ||
      artist.yearsActiveEnd.year >= activeAfter) &&
      (artist.yearsActiveStart.year <= activeBefore))))
    // // check elements contain genre
    // for {
    //   artist <- artists
    //   genre <- genres
    //   location <- locations
    //   if genre.isEmpty || artist.genre == genre
    //   if artist.origin == location || location.isEmpty
    //   if artist.yearsActiveStart >= activeAfter || activeAfter == 0
    //   if artist.yearsActiveEnd <= activeBefore || activeBefore == 0
    // } yield artist
  }

  def main(args: Array[String]): Unit = {
    println(searchArtists(artists, List("Pop"), List("England"), true, 1950, 2022))
    println(searchArtists(artists, List.empty, List("England"), true, 1950, 2022))
    println(searchArtists(artists, List.empty, List.empty, true, 1950, 1979))
  }
}
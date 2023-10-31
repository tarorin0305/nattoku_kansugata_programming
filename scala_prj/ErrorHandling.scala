object ErrorHandling {
  val rawShows = List("Breaking Bad (2008-2013)", "The Wire (2002-2008)", "Mad Men (2007-2015)")
  // parse String to TvShow
  def parsShow(rawShow: String): TvShow = {
    val bracketOpen = rawShow.indexOf('(')
    val bracketClose = rawShow.indexOf(')')
    val dash = rawShow.indexOf('-')

    val title = rawShow.substring(0, bracketOpen).trim
    val yearStart = Integer.parseInt(rawShow.substring(bracketOpen + 1, dash))
    val yearEnd = Integer.parseInt(rawShow.substring(dash + 1, bracketClose))

    TvShow(title, yearStart, yearEnd)
  }
  
  def parseShows(rawShows: List[String]): List[TvShow] = {
    rawShows.map(parsShow)
  }

  // TV Show Class
  case class TvShow(title: String, start: Int, end: Int)
  // sort by longer term
  def sortShows(shows: List[TvShow]): List[TvShow] = {
    shows
      .sortBy(tvShow => tvShow.end - tvShow.start)
      .reverse
  }

  def sortRawShows(rawShows: List[String]): List[TvShow] = {
    val shows = parseShows(rawShows)
    sortShows(shows)
  }
  val shows = List(TvShow("Breaking Bad", 2008, 2013), TvShow("The Wire", 2002, 2008),TvShow("Mad Men", 2007, 2015))
  
  def main(args: Array[String]): Unit = {
    // println(parseShow(rawShows))
    println(sortShows(shows))
    println(parsShow("Breaking Bad (2008-2013)"))
    println(parseShows(rawShows))
    // raise error
    parseShow("Breaking Bad 2008-2013")
  }
}
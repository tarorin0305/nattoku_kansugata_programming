object ErrorHandling {
  val rawShows = List("Breaking Bad (2008-2013)", "The Wire (2002-2008)", "Mad Men (2007-2015)")
  // parse String to TvShow
  def parseShow(rawShow: String): Either[String, TvShow] = {
    for {
      name <- extractName(rawShow)
      yearStart <- extractYearStart(rawShow).orElse(extractSingleYear(rawShow))
      yearEnd <- extractYearEnd(rawShow).orElse(extractSingleYear(rawShow))
    } yield TvShow(name, yearStart, yearEnd)
  }

  def parseShows(rawShows: List[String]): Either[String, List[TvShow]] = {
    val initialResult: Either[String, List[TvShow]] = Right(List.empty)
    rawShows
      .map(parseShow)
      .foldLeft(initialResult)(addOrResign)
  }

  def addOrResign(parsedShows: Either[String, List[TvShow]], newParsedShow: Either[String, TvShow]): Either[String, List[TvShow]] = {
    for {
      shows <- parsedShows
      parsedShow <- newParsedShow
    } yield shows.appended(parsedShow)
  }

  def extractName(rawShow: String): Either[String, String] = {
    val bracketOpen = rawShow.indexOf('(')
    if (bracketOpen > 0)
      Right(rawShow.substring(0, bracketOpen).trim)
    else Left(s"Could not extract name from $rawShow")
  }

  def extractYearStart(rawShow: String): Either[String, Int] = {
    val bracketOpen = rawShow.indexOf('(')
    val dash = rawShow.indexOf('-')
    for {
      yearStartStr <- if (bracketOpen != -1 && dash > bracketOpen + 1)
                        Right(rawShow.substring(bracketOpen + 1, dash))
                      else Left(s"Could not extract year start from $rawShow")
      yearStart <- yearStartStr.toIntOption.toRight(s"Could not parse $yearStartStr")
    } yield yearStart
  }

  def extractYearEnd(rawShow: String): Either[String, Int] = {
    val bracketClose = rawShow.indexOf(')')
    val dash = rawShow.indexOf('-')
    for {
      yearEndStr <- if (bracketClose != -1 && bracketClose > dash + 1)
                      Right(rawShow.substring(dash + 1, bracketClose))
                    else Left(s"Could not extract year end from $rawShow")
      yearEnd <- yearEndStr.toIntOption.toRight(s"Could not parse $yearEndStr")
    } yield yearEnd
  }

  def extractSingleYear(rawShow: String): Either[String, Int] = {
    val dash = rawShow.indexOf('-')
    val bracketOpen = rawShow.indexOf('(')
    val bracketClose = rawShow.indexOf(')')
    for {
      singleYearStr <- if (dash == -1 && bracketOpen > 0 && bracketClose > 0) {
                          Right(rawShow.substring(bracketOpen + 1, bracketClose))
                        }
                        else Left(s"Could not extract single year from $rawShow")
      singleYear <- singleYearStr.toIntOption.toRight(s"Could not parse $singleYearStr as Int")
    } yield singleYear
  }

  def extractSingleYearOrYearEnd(rawShow: String): Either[String, Int] = {
    extractSingleYear(rawShow).orElse(extractYearEnd(rawShow))
  }

  def extractStartOrEndYearOrSingleYear(rawShow: String): Either[String, Int] = {
    extractYearStart(rawShow).orElse(extractYearEnd(rawShow)).orElse(extractSingleYear(rawShow))
  }

  def extractSingleYearIffValidName(rawShow: String): Either[String, Int] = {
    for {
      name <- extractName(rawShow)
      singleYear <- extractSingleYear(rawShow)
    } yield singleYear
  }

  def extractStartYearOrEndYearOrSingleYearIffValidName(rawShow: String): Either[String, Int] = {
    for {
      name <- extractName(rawShow)
      year <- extractYearStart(rawShow).orElse(extractYearEnd(rawShow)).orElse(extractSingleYear(rawShow))
    } yield year
  }

  // def _parseShows(rawShows: List[String]): List[TvShow] = {
  //   rawShows.map(parseShow).map(show => show.toList).flatten
  // }

  // TV Show Class
  case class TvShow(title: String, start: Int, end: Int)
  // sort by longer term
  def sortShows(shows: List[TvShow]): List[TvShow] = {
    shows
      .sortBy(tvShow => tvShow.end - tvShow.start)
      .reverse
  }

  // def sortRawShows(rawShows: List[String]): List[TvShow] = {
  //   val shows = parseShows(rawShows)
  //   sortShows(shows)
  // }
  val shows = List(TvShow("Breaking Bad", 2008, 2013), TvShow("The Wire", 2002, 2008),TvShow("Mad Men", 2007, 2015))

  def main(args: Array[String]): Unit = {
    val rawShows = List("The Wire (2002-2008)", "Chernobyl (2019)", "Breaking Bad 2008-2013")

    println(parseShow("Breaking Bad 2008-2013"))
    println(parseShow("Breaking Bad (2008-2013)"))
    println(parseShow("Chernobyl (2019)"))
    println(parseShow("Mad Men (-2015)"))
    println("------------------")
    println(parseShows(rawShows))
    println(parseShows(List("Chernobyl (2019)", "Breaking Bad (2008-2013)")))
  }
}
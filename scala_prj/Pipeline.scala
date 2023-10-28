object Pipeline {
  case class Book(title: String, authors: List[String])
  val books = List(
      Book("FP in Scala", List("Chiusano", "Bjarnason")),
      Book("The Hobbit", List("Tolkien")),
      Book("Modern Java in Action", List("Urma", "Fusco", "Mycroft"))
    )

  case class Movie(title: String)
  val authors = List("Chiusano", "Bjarnason", "Tolkien", "Urma", "Fusco", "Mycroft")

  case class Point(x: Int, y: Int)
  case class Point3d(x: Int, y: Int, z: Int)
  val xs = List(1)
  val ys = List(-2, 7)
  val zs = List(3, 4)

  def isInside(point: Point, radius: Int): Boolean = {
    radius * radius >= point.x * point.x + point.y * point.y
  }

  def vaildRadiusFilter(radius: Int): List[Int] = {
    if (radius >= 0) List(radius) else List.empty
  }

  def bookAdaptions(author: String): List[Movie] = {
    if (author == "Tolkien") {
      List(Movie("An Unexpected Journey"), Movie("The Desolation of Smaug"))
    } else {
      List.empty
    }
  }

  def recommendedBooks(friend: String): List[Book] = {
    val scala = List(
      Book("FP in Scala", List("Chiusano", "Bjarnason")), Book("Get Programming with Scala", List("Sfregola")))
    val fiction = List(
      Book("Harry Potter", List("Rowling")), Book("The Lord of the Rings", List("Tolkien")))

    // return books by friend
    if(friend == "Alice") scala
    else if(friend == "Bob") fiction
    else List.empty
  }

  val friends = List("Alice", "Bob", "Charlie")

  def recommendationFeed(books: List[Book]) = ???

  // Event Class
  case class Event(name: String, start: Int, end: Int)

  // def parse(name: String, start: Int, end: Int): Option[Event] = {
  //   if (name.size > 0 && end < 3000 && start <= end)
  //     Some(Event(name, start, end))
  //   else
  //     None
  // }

  def validateName(name: String): Option[String] = {
    if (name.size > 0) Some(name) else None
  }

  def validateEnd(end: Int): Option[Int] = {
    if (end < 3000) Some(end) else None
  }

  def validateStart(start: Int, end: Int): Option[Int] = {
    if (start <= end) Some(start) else None
  }

  def parse(name: String, start: Int, end: Int): Option[Event] = {
    for {
      validName <- validateName(name)
      validEnd <- validateEnd(end)
      validStart <- validateStart(start, validEnd)
    } yield Event(validName, validStart, validEnd)
  }

  def validateLength(start: Int, end: Int, minLength: Int): Option[Int] = {
    if (end - start >= minLength) Some(end - start) else None
  }

  def parseLongEvent(name: String, start: Int, end: Int, minLength: Int): Option[Event] = {
    for {
      validName <- validateName(name)
      validStart <- validateStart(start, end)
      validEnd <- validateEnd(end)
      validLength <- validateLength(validStart, validEnd, minLength)
    } yield Event(validName, validStart, validEnd)
  }

  def main(args: Array[String]): Unit = {
    val points = List(Point(1, 1), Point(5, 2), Point(3, 3))
    val radiuses = List(2, 1)
    val riskyRadiuses = List(-10, 0, 2)
    println(parseLongEvent("Apollo Program", 1961, 1972, 10))
    println(parseLongEvent("Apollo Program2", 1961, 1972, 20))
    println(parseLongEvent("", 1961, 1972, 30))
    println(parseLongEvent("Apollo Program3", 1991, 1972, 5))
  }
}
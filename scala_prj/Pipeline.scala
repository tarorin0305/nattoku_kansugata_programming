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

  case class Event(name: String, start: Int, end: Int)
  def parse(name: String, start: Int, end: Int): Option[Event] = {
    if (name.size > 0 && end < 3000 && start <= end)
      Some(Event(name, start, end))
    else
      None
  }

  def main(args: Array[String]): Unit = {
    val points = List(Point(1, 1), Point(5, 2), Point(3, 3))
    val radiuses = List(2, 1)
    val riskyRadiuses = List(-10, 0, 2)
    val result = for {
      r <- radiuses
      point <- points.filter(p => isInside(p, r))
    } yield s"$point is inside within a radius of $r"
    println(result)

    val result2 = for {
      r <- radiuses
      point <- points
      if isInside(point, r)
    } yield s"$point is inside within a radius of $r"
    println(result2)

    val result3 = for {
      r <- riskyRadiuses.filter(radius => radius >= 0)
      point <- points.filter(p => isInside(p, r))
    } yield s"$point is inside within a radius of $r"

    val result4 = for {
      r <- riskyRadiuses
      if r >= 0
      point <- points.filter(p => isInside(p, r))
    } yield s"$point is inside within a radius of $r"

    val result5 = for {
      radius <- riskyRadiuses
      // flatMapに渡せる関数を使用している。 つまり集合の要素を受け取り、集合(空集合を含む)を返す関数
      validRadius <- vaildRadiusFilter(radius)
      point <- points.filter(p => isInside(p, validRadius))
    } yield s"$point is inside within a radius of $validRadius"
    // println val result3 to 5
    println(result3)
    println(result4)
    println(result5)
  }
}
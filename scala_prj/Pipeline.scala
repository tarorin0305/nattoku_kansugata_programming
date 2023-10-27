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

  def main(args: Array[String]): Unit = {
    val result = books.flatMap(book =>
      book.authors.flatMap(author =>
        bookAdaptions(author).map(movie =>
          s"You may like ${movie.title}, " +
          s"because you lied $author's ${book.title}"
        )
      )
    )
    println(result)

    val result2 = for {
      book <- books
      author <- book.authors
      movie <- bookAdaptions(author)
    } yield s"You may like ${movie.title}, " +
            s"because you liked $author's ${book.title}"
    println(result2)

    val result3 = for {
      x <- xs
      y <- ys
      z <- zs
    } yield Point3d(x, y, z)
    println(result3)

    val result4 = xs.flatMap( x =>
      ys.flatMap( y =>
        zs.map(z => Point3d(x, y, z)))
    )
    println(result4)

  }
}
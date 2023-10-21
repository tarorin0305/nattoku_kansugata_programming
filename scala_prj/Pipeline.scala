object Pipeline {
  case class Book(title: String, authors: List[String])
  val books = List(
      Book("FP in Scala", List("Chiusano", "Bjarnason")),
      Book("The Hobbit", List("Tolkien")),
      Book("Modern Java in Action", List("Urma", "Fusco", "Mycroft"))
    )

  case class Movie(title: String)
  def bookAdaptions(author: String): List[Movie] = {
    if (author == "Tolkien") {
      List(Movie("An Unexpected Journey"), Movie("The Desolation of Smaug"))
    } else {
      List.empty
    }
  }

  def recommendationFeed(books: List[Book]) = ???

  def main(args: Array[String]): Unit = {
    println(books
      .map(book => book.title)
      .filter(title => title.contains("Scala"))
      .size)
  }
}
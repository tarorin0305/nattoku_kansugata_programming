object Pipeline {
  case class Book(title: String, authors: List[String])
  val books = List(
      Book("FP in Scala", List("Chiusano", "Bjarnason")),
      Book("The Hobbit", List("Tolkien")),
      Book("Modern Java in Action", List("Urma", "Fusco", "Mycroft"))
    )

  def main(args: Array[String]): Unit = {
    println(books
      .map(book => book.title)
      .filter(title => title.contains("Scala"))
      .size)
  }
}
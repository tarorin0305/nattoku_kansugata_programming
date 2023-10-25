object Pipeline {
  case class Book(title: String, authors: List[String])
  val books = List(
      Book("FP in Scala", List("Chiusano", "Bjarnason")),
      Book("The Hobbit", List("Tolkien")),
      Book("Modern Java in Action", List("Urma", "Fusco", "Mycroft"))
    )

  case class Movie(title: String)
  val authors = List("Chiusano", "Bjarnason", "Tolkien", "Urma", "Fusco", "Mycroft")

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
    println(books
      .map(book => book.title)
      .filter(title => title.contains("Scala"))
      .size)

      println(books.map(book => book.authors).flatten)
      println(books.flatMap(book => book.authors))
      println(books.flatMap(_.authors))
      // print split marks
      println("-----")
      println(authors.map(bookAdaptions).flatten)
      println(books.flatMap(_.authors).flatMap(bookAdaptions))
      println("-----")
      val recommendations = friends.flatMap(recommendedBooks)
      val recommendedAuthors = recommendations.flatMap(_.authors)
      println(recommendations)
      println(recommendedAuthors)
      println(friends.flatMap(recommendedBooks).flatMap(_.authors))

      val result = books.flatMap(book =>
        book.authors.flatMap(author =>
          bookAdaptions(author).map(movie =>
            s"You may like ${movie.title}, " +
            s"because you lied $author's ${book.title}"
          )
        )
      )
      println(result)
  }
}
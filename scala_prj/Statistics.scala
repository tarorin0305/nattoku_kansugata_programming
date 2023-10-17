object Statistics {
  def rankedWords(wordScore: String => Int, words: List[String]): List[String] = {
    words.sortBy(wordScore).reverse
  }

  def score(word: String): Int = {
    word.replaceAll("a", "").length
  }

  def bonus(word: String): Int = {
    if (word.contains("c")) 5 else 0
  }

  def penalty(word: String): Int = {
    if (word.contains("s")) 7 else 0
  }

  def wordScores(wordScore: String => Int, words: List[String]): List[Int] = {
    words.map(wordScore)
  }

  def highScoringWords(wordScore: String => Int, words: List[String]): List[String] = {
    words.filter(w => wordScore(w) > 1)
  }

  def wordLengthLess5(word: String): Boolean = {
    word.length() < 5
  }

  def wordContainsS(word: String): Boolean = {
    word.count(_ == 's') > 2
  }

  def NumberIsOdd(num: Int): Boolean = {
    num % 2 == 1
  }

  def main(args: Array[String]): Unit = {
    println(rankedWords(w => score(w), List("rust", "java", "scala")))
    println(rankedWords(w => score(w) + bonus(w), List("rust", "java", "scala")))
    println(rankedWords(w => score(w) + bonus(w) - penalty(w), List("rust", "java", "scala")))
    println("--------------------")
    println(wordScores(w => score(w) + bonus(w) - penalty(w), List("ada", "haskell", "scala", "java", "rust")))
    println(wordScores(w => w.length, List("scala", "rust", "ada")))
    println(wordScores(w => w.count(_ == 's'), List("rust", "ada")))
    println(List(5, 1, 2, 4, 0).map(int => -int))
    println(List(5, 1, 2, 4, 0).map(int => int * 2))
    println("--------------------")
    println(List("haskell", "rust", "ada", "scala", "java"))
    val words = List("haskell", "rust", "ada", "scala", "java")
    println(highScoringWords(w => score(w) + bonus(w) - penalty(w), words))
    println("----------------------------------------")
    println(List("scala", "rust", "ada").filter(word => word.length() < 5))
    println(List("scala", "rust", "ada").filter(wordLengthLess5))
    println(List("scala", "rust", "ada", "sss").filter(word => word.count(_ == 's') > 2))
    println(List("scala", "rust", "ada", "sss").filter(wordContainsS))
    println(List(5, 1, 2, 4, 0).filter(word => word % 2 == 1))
    println(List(5, 1, 2, 4, 0).filter(NumberIsOdd))
    println(List(5, 1, 2, 4, 0).filter(word => word > 4))
  }
}
object Main {
  def firstTwo(list: List[String]): List[String] = {
    list.slice(0, 2)
  }

  def lastTwo(list: List[String]): List[String] = {
    list.slice(list.length - 2, list.length)
  }

  def movedFirstTwoToEnd(list: List[String]): List[String] = {
    val firstTwo = list.slice(0, 2)
    val rest = list.slice(2, list.length)
    rest.appendedAll(firstTwo)
  }

  def insertBeforeLast(list: List[String], item: String): List[String] = {
    val firstToBeforeLast = list.slice(0, list.length - 1)
    val last = list.slice(list.length - 1, list.length)
    firstToBeforeLast.appended(item).appendedAll(last)
  }

  def getDiscountPercentage(items: List[String]): Int = {
    if (items.contains("book")) {
      5
    } else {
      0
    }
  }

  def getTipPercentage(names: List[String]): Int = {
    if (names.length > 5) {
      20
    } else if (names.length > 0) {
      10
    } else {
      0
    }
  }

  def add(a: Int, b: Int): Int = {
    return a + b
  }

  def increment(x: Int): Int = {
    return x + 1
  }

  def getFirstCharacter(str: String): Char = {
    return str.charAt(0)
  }

  def wordScoreFunc(word: String): Int = {
    return word.replaceAll("a", "").length
  }

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

  def scoreWithBonus(word: String): Int = {
    val base = score(word)
    if (word.contains("c")) base + 5 else base
  }

  def sortWords(wordSortFunc: String => Int, words: List[String]): List[String] = {
    words.sortBy(wordSortFunc)
  }

  def sortIntWords(wordSortFunc: Int => Int, words: List[Int]): List[Int] = {
    words.sortBy(wordSortFunc)
  }

  def wordSortByLength(word: String): Int = {
    word.length
  }

  def wordSortByCountOfS(word: String): Int = {
    word.count(_ == 's')
  }

  def wordSortByBigOrder(word: Int): Int = {
    -word
  }

  def wordSortByCountOfSByBigOrder(word: String): Int = {
    -word.count(_ == 's')
  }

  def main(args: Array[String]): Unit = {
    println(rankedWords(w => score(w), List("rust", "java", "scala")))
    println(rankedWords(w => score(w) + bonus(w), List("rust", "java", "scala")))
    println(rankedWords(w => score(w) + bonus(w) - penalty(w), List("rust", "java", "scala")))
  }

  // def score(word: String): Int = {
  //   word.replaceAll("a", "").length
  // }

  // val words = List("rust", "java")

  // words.sortBy(score)
}
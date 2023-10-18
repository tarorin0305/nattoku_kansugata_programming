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

  def higherNumList(numList: List[Int]): Int => List[Int] = {
    higherNum => numList.filter(num => num > higherNum)
  }

  def modSpecificNumList(numList: List[Int]): Int => List[Int] = {
    specificNum => numList.filter(num => num % specificNum == 0)
  }

  def lessSpecificNumWordsList(words: List[String]): Int => List[String] = {
    // Int => List[String]
    specificNum => words.filter(word => word.length() < specificNum)
  }

  def moreSpecificNumOfS(wordList: List[String]): Int => List[String] = {
    // Int => List[String]
    specificNum => wordList.filter(word => word.count(_ == 's') >= specificNum)
  }

  def highScoringWords(wordScore: String => Int): Int => List[String] => List[String] = {
    higherThan =>
      words =>
        words.filter(word => wordScore(word) > higherThan)
  }

  def main(args: Array[String]): Unit = {
    val funcHigherNumList = higherNumList(List(5, 1, 2, 4, 0))
    println(funcHigherNumList(4))
    println(funcHigherNumList(1))
    val funcModSpecificNumList = modSpecificNumList(List(5, 1, 2, 4, 15))
    println(funcModSpecificNumList(5))
    println(funcModSpecificNumList(2))
    val funcLessSpecificNumWordsList = lessSpecificNumWordsList(List("scala", "rust", "ada", "8888888"))
    println(funcLessSpecificNumWordsList(4))
    println(funcLessSpecificNumWordsList(7))
    val funcMoreSpecificNumOfS = moreSpecificNumOfS(List("scala", "rust", "ada", "sss"))
    println(funcMoreSpecificNumOfS(3))
    println(funcMoreSpecificNumOfS(1))
  }
}
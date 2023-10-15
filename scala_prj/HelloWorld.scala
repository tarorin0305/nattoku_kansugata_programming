object Main {
  def main(args: Array[String]): Unit = {
    println("Hello, world!")
    println(add(1, 2))
    println(increment(1))
    println(getFirstCharacter("Hello"))
    println(wordScore("Hello"))
  }

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

  def wordScore(word: String): Int = {
    return word.length()
  }
}
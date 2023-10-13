object Main {
  def main(args: Array[String]): Unit = {
    println("Hello, world!")
    println(add(1, 2))
    println(increment(1))
    println(getFirstCharacter("Hello"))
    println(wordScore("Hello"))
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
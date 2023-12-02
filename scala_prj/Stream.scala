import java.util.Random
import cats.effect.IO
import cats.effect.unsafe.implicits.global
import cats.implicits.catsSyntaxApplicativeError
import cats.syntax.all.catsSyntaxApplicativeError
import cats.syntax.applicativeError.catsSyntaxApplicativeError
import scala.collection.mutable
import java.math.{BigDecimal, RoundingMode}
import scala.collection.mutable.HashMap
import model.Currency

object model {
  opaque type Currency = String
  object Currency {
    def apply(name: String): Currency = name
    extension (currency: Currency) {
      def name: String = currency
    }
  }
}
object Stream {

  def exchangeRatesTableApiCall(
      currency: String
  ): HashMap[String, BigDecimal] = {
    val rand = new Random()
    if (rand.nextFloat() < 0.25) throw new RuntimeException("Connection error")
    val result = HashMap[String, BigDecimal]()
    if (currency == "USD") {
      result.put(
        "EUR",
        BigDecimal
          .valueOf(0.81 + (rand.nextGaussian() / 100))
          .setScale(2, RoundingMode.FLOOR)
      )
      result.put(
        "JPY",
        BigDecimal
          .valueOf(103.25 + (rand.nextGaussian()))
          .setScale(2, RoundingMode.FLOOR)
      )
      result
    } else {
      throw new RuntimeException("Rate not available")
    }
  }

  def exchangeTable(from: Currency): IO[Map[Currency, BigDecimal]] = {
    ???
  }

  def main(args: Array[String]): Unit = {
    val m1: Map[String, String] = Map("key" -> "value")
    val m2: Map[String, String] = m1.updated("key2", "value2")
    val m3: Map[String, String] = m2.removed("key2").updated("key2", "another2")
    val m4: Map[String, String] = m3.removed("key")
    val valueFromM3: Option[String] = m3.get("key")
    val valueFromM4: Option[String] = m4.get("key")

    // println all val above defined
    println(m1)
    println(m2)
    println(m3)
    println(m4)
    println(valueFromM3)
    println(valueFromM4)
  }
}

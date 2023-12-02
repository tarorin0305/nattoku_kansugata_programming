import java.util.Random
import cats.effect.IO
import cats.effect.unsafe.implicits.global
import cats.implicits.catsSyntaxApplicativeError
import cats.syntax.all.catsSyntaxApplicativeError
import cats.syntax.applicativeError.catsSyntaxApplicativeError
import scala.collection.mutable
import java.math.{BigDecimal, RoundingMode}
import scala.collection.mutable.HashMap

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

  def main(args: Array[String]): Unit = {
    val currency = "USD"
    val rates = exchangeRatesTableApiCall(currency)
    println(rates)
    // println(s"Exchange rates for $currency: $rates")
  }
}

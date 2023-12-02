import java.util.Random
import cats.effect.IO
import cats.effect.unsafe.implicits.global
import cats.implicits.catsSyntaxApplicativeError
import cats.syntax.all.catsSyntaxApplicativeError
import cats.syntax.applicativeError.catsSyntaxApplicativeError
import scala.collection.mutable
import java.math.{BigDecimal, RoundingMode}
import scala.math.Ordered.orderingToOrdered
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
  def retry[A](action: IO[A], maxRetries: Int): IO[A] = {
    List
      .range(0, maxRetries)
      .map(_ => action)
      .foldLeft(action)((program, retryAction) => program.orElse(retryAction))
  }

  def exchangeRatesTableApiCall(
      currency: String
  ): Map[String, BigDecimal] = {
    val rand = new Random()
    if (rand.nextFloat() < 0.25) throw new RuntimeException("Connection error")
    val result = Map[String, BigDecimal]()
    if (currency == "USD") {
      val result2 = result + ("EUR" -> BigDecimal
        .valueOf(0.81 + (rand.nextGaussian() / 100))
        .setScale(2, RoundingMode.FLOOR))
      val result3 = result2 + ("JPY" -> BigDecimal
        .valueOf(103.25 + (rand.nextGaussian()))
        .setScale(2, RoundingMode.FLOOR))
      result3
    } else {
      throw new RuntimeException("Rate not available")
    }
  }

  def exchangeTable(from: Currency): IO[Map[Currency, BigDecimal]] = {
    IO.delay(exchangeRatesTableApiCall(from.name))
      .map(table =>
        table.map(kv =>
          kv match {
            case (currencyName, rate) => (Currency(currencyName), rate)
          }
        )
      )
  }

  def trending(rates: List[BigDecimal]): Boolean = {
    rates.size > 1 && rates
      .zip(rates.drop(1))
      .forall(ratePair =>
        ratePair match {
          case (previousRate, rate) => rate > previousRate
        }
      )
  }

  def extractSingleCurrencyRate(
      currencyToExtract: Currency
  )(table: Map[Currency, BigDecimal]): Option[BigDecimal] = {
    table.get(currencyToExtract)
  }

  def lastRates(from: Currency, to: Currency): IO[List[BigDecimal]] = {
    for {
      table1 <- retry(exchangeTable(from), 10)
      table2 <- retry(exchangeTable(from), 10)
      table3 <- retry(exchangeTable(from), 10)
      lastTables = List(table1, table2, table3)
    } yield lastTables.flatMap(extractSingleCurrencyRate(to))
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

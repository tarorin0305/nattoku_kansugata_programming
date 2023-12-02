import java.util.Random
import cats.effect.IO
import cats.effect.unsafe.implicits.global
import cats.implicits.catsSyntaxApplicativeError
import cats.syntax.all.catsSyntaxApplicativeError
import cats.syntax.applicativeError.catsSyntaxApplicativeError
import scala.collection.mutable
import scala.math.Ordered.orderingToOrdered
import scala.collection.mutable.HashMap
import model.Currency
import scala.math.BigDecimal
import scala.math.BigDecimal.RoundingMode
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

  def lastRates(from: Currency, to: Currency, n: Int): IO[List[BigDecimal]] = {
    if (n < 1) {
      IO.pure(List.empty)
    } else {
      for {
        currencyRate <- currencyRate(from, to)
        remainingRates <-
          if (n == 1) IO.pure(List.empty)
          else lastRates(from, to, n - 1)
      } yield remainingRates.prepended(currencyRate)
    }
  }

  def exchangeIfTrending(
      amount: BigDecimal,
      from: Currency,
      to: Currency
  ): IO[BigDecimal] = {
    for {
      rates <- lastRates(from, to, 3)
      result <-
        if (trending(rates)) IO.pure(amount * rates.last)
        else exchangeIfTrending(amount, from, to)
    } yield result
  }

  def currencyRate(from: Currency, to: Currency): IO[BigDecimal] = {
    for {
      table <- retry(exchangeTable(from), 10)
      rate <- extractSingleCurrencyRate(to)(table) match {
        case Some(value) => IO.pure(value)
        case None        => currencyRate(from, to)
      }
    } yield rate
  }

  def main(args: Array[String]): Unit = {
    val result =
      exchangeIfTrending(BigDecimal(1000), Currency("USD"), Currency("EUR"))
    println(result)
  }
}

import java.util.Random
import cats.effect.IO
import cats.effect.unsafe.implicits.global
import cats.implicits.catsSyntaxApplicativeError
import cats.syntax.all.catsSyntaxApplicativeError
import cats.syntax.applicativeError.catsSyntaxApplicativeError
import scala.collection.mutable
import scala.math.Ordered.orderingToOrdered
import scala.collection.mutable.HashMap
import modelCity.City
import scala.math.BigDecimal
import scala.math.BigDecimal.RoundingMode
import fs2.Stream
import modelCity.CityStatus

object modelCity {
  opaque type City = String
  object City {
    def apply(name: String): City = name
    extension (city: City) {
      def name: String = city
    }
  }
  case class CityStatus(city: City, checkIns: Int)
}
object CityExam {
  def processCheckIns(checkIns: Stream[IO, City]): IO[Unit] = {
    checkIns
      .scan(Map.empty[City, Int])((cityCheckIns, city) =>
        cityCheckIns.updatedWith(city)(_.map(_ + 1).orElse(Some(1)))
      )
      .map(topCities)
      .foreach(IO.println)
      .compile
      .drain
  }
  def topCities(cityCheckIns: Map[City, Int]): List[CityStatus] = {
    cityCheckIns.toList
      .map(
        _ match {
          case (city, checkIns) => CityStatus(city, checkIns)
        }
      )
      .sortBy(_.checkIns)
      .reverse
      .take(3)
  }

  def main(args: Array[String]): Unit = {
    val checkIns: Stream[IO, City] = Stream(
      City("Sydney"),
      City("Sydney"),
      City("Cape Town"),
      City("Singapore"),
      City("Cape Town"),
      City("Sydney")
    ).covary[IO]

    val checkInsLong: Stream[IO, City] =
      Stream(
        City("Sydney"),
        City("Dublin"),
        City("Cape Town"),
        City("Lima"),
        City("Singapore")
      )
        .repeatN(100_000)
        .append(Stream.range(0, 100_000).map(i => City(s"City $i")))
        .append(Stream(City("Sydney"), City("Sydney"), City("Lima")))
        .covary[IO]

    processCheckIns(checkIns).unsafeRunSync()
  }
}

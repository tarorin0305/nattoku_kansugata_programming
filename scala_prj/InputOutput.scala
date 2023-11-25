import java.util.Random
import cats.effect.IO
import cats.effect.unsafe.implicits.global
import cats.implicits.catsSyntaxApplicativeError
import cats.syntax.all.catsSyntaxApplicativeError
import cats.syntax.applicativeError.catsSyntaxApplicativeError

object InputOutput {
  case class MeetingTime(startHour: Int, endHour: Int)

  def calendarEntriesApiCall(name: String): List[MeetingTime] = {
    val rand = new Random()
    if (rand.nextFloat() < 0.25) {
      throw new RuntimeException("API call failed")
    }
    name match {
      case "Alice" => List(MeetingTime(8, 10), MeetingTime(11, 12))
      case "Bob" => List(MeetingTime(9, 10))
      case _ => List(MeetingTime(rand.nextInt(5) + 8, rand.nextInt(4) + 13))
    }
  }

  def calendarEntries(name: String): IO[List[MeetingTime]] = {
    IO.delay(calendarEntriesApiCall(name))
  }

  def createMeetingApiCall(names: List[String], meetingTime: MeetingTime): Unit = {
    val random = new Random()
    if (random.nextFloat() < 0.25) {
      throw new RuntimeException("Failed to create meeting")
    }
    println("SIDE-EFFECT")
  }

  def createMeeting(names: List[String], meeting: MeetingTime): IO[Unit] = {
    IO.delay(createMeetingApiCall(names, meeting))
  }

  def meetingsOverlap(meeting1: MeetingTime, meeting2: MeetingTime): Boolean = {
    meeting1.endHour > meeting2.startHour && meeting1.startHour < meeting2.endHour
  }

  def possibleMeetings(existingMeetings: List[MeetingTime], startHour: Int, endHour: Int, lengthHours: Int): List[MeetingTime] = {
    val slots = List
                .range(startHour, endHour - lengthHours + 1)
                .map(start => MeetingTime(start, start + lengthHours))
    slots.filter(slot => existingMeetings.forall(meeting => !meetingsOverlap(meeting, slot)))
  }

  def schedule(person1: String, person2: String, lengthHours: Int): IO[Option[MeetingTime]] = {
    for {
      existingMeetings <- scheduleMeeting(person1, person2)
      meetings = possibleMeetings(existingMeetings, 8, 16, lengthHours)
    } yield meetings.headOption
  }

  def scheduleMeeting(person1: String, person2: String): IO[List[MeetingTime]] = {
    for {
      person1Calendar <- calendarEntries(person1)
      person2Calendar <- calendarEntries(person2)
    } yield person1Calendar.appendedAll(person2Calendar)
  }

  def castTheDie(): Int = {
    // throw new RuntimeException("Die is broken") randomly in 25% of cases
    if (new Random().nextFloat() < 0.25) {
      throw new RuntimeException("Die is broken")
    }

    return new Random().nextInt(6) + 1
  }

  def drawAPointCard(): Int = {
    // throw new RuntimeException("Card deck is empty") randomly in 25% of cases
    if (new Random().nextFloat() < 0.25) {
      throw new RuntimeException("Card deck is empty")
    }

    return new Random().nextInt(14) + 1
  }

  def main(args: Array[String]): Unit = {
    // var scheduledMeetingProgram = scheduleMeeting("Alice", "Bob")
    // println(scheduledMeetingProgram.unsafeRunSync())

    val result1 = IO.delay(castTheDie()).orElse(IO.pure(0))
    println(result1.unsafeRunSync())
    val result2 = IO.delay(drawAPointCard()).orElse(IO.delay(castTheDie()))
    println(result2.unsafeRunSync())
    val result3 = IO.delay(castTheDie()).orElse(IO.delay(castTheDie())).orElse(IO.pure(0))
    println(result3.unsafeRunSync())
    val result4 = for {
      die <- IO.delay(castTheDie()).orElse(IO.pure(0))
      card <- IO.delay(drawAPointCard()).orElse(IO.pure(0))
    } yield die + card
    println(result4.unsafeRunSync())
    val result5 = (for {
      card <- IO.delay(drawAPointCard())
      die1 <- IO.delay(castTheDie())
      die2 <- IO.delay(castTheDie())
    } yield card + die1 + die2).orElse(IO.pure(0))
    println(result5.unsafeRunSync())
  }
}
import java.util.Random
import cats.effect.IO
import cats.effect.unsafe.implicits.global

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

  def scheduleMeeting(person1: String, person2: String): IO[List[MeetingTime]] = {
    for {
      person1Calendar <- calendarEntries(person1)
      person2Calendar <- calendarEntries(person2)
    } yield person1Calendar.appendedAll(person2Calendar)
  }

  def main(args: Array[String]): Unit = {
    var scheduledMeetingProgram = scheduleMeeting("Alice", "Bob")
    println(scheduledMeetingProgram.unsafeRunSync())
  }
}
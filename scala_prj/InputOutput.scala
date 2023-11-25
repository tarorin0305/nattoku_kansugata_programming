import java.util.Random
// import IO from cats.effect.IO
import cats.effect.IO
import cats.effect.unsafe.implicits.global

object InputOutput {
  case class MeetingTime(startHour: Int, endHour: Int)

  def calendarEntriesApiCall(name: String): List[MeetingTime] = {
    ???
  }

  def calendarEntries(name: String): IO[List[MeetingTime]] = {
    IO.delay(calendarEntriesApiCall(name))
  }

  def createMeetingApiCall(names: List[String], meetingTime: MeetingTime): Unit = {
    ???
    // static void createMeetingApiCall(List<String> names, MeetingTime meetingTime) {
    //   Random random = new Random();
    //   if (random.nextFloat() < 0.25) {
    //     throw new RuntimeException("Failed to create meeting");
    //   }
    //   System.out.printf("SIDE-EFFECT")
    // }
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

  }
}
import java.util.Random
object InputOutput {
  case class MeetingTime(startHour: Int, endHour: Int)

  def calendarEntriesApiCall(name: String): List[MeetingTime] = {
    ???
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

  def main(args: Array[String]): Unit = {

  }
}
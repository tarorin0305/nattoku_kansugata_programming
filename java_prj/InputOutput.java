import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.function.Function;
import java.util.stream.Collectors;

public class InputOutput {
    static MeetingTime schedule(String person1, String person2, int lengthHours) {
        // list of meeting times
        List<MeetingTime> person1Entries = calendarEntriesApiCall(person1);
        List<MeetingTime> person2Entries = calendarEntriesApiCall(person2);

        List<MeetingTime> scheduledMeetings = new ArrayList<>();
        scheduledMeetings.addAll(person1Entries);
        scheduledMeetings.addAll(person2Entries);

        //  勤務時間(8~16時)内で指定可能な長さ(lengthHours)の時間枠をすべて生成する。
        List<MeetingTime> slots = new ArrayList<>();
        for (int startHour = 8; startHour < 16 - lengthHours; startHour++) {
            slots.add(new MeetingTime(startHour, startHour + lengthHours));
        }

        List<MeetingTime> possibleMeetings = new ArrayList<>();
        for (var slot : slots) {
            var meetingPossible = true;
            for (var meeting : scheduledMeetings) {
                if (slot.endHour > meeting.startHour && meeting.endHour > slot.startHour) {
                    meetingPossible = false;
                    break;
                }
            }
            if (meetingPossible) {
                possibleMeetings.add(slot);
            }
        }

        if (!possibleMeetings.isEmpty()) {
            createMeetingApiCall(List.of(person1, person2), possibleMeetings.get(0));
            return possibleMeetings.get(0);
        } else {
            return null;
        }
    }
    public static void main(String[] args) {
        schedule("Alice", "Bob", 1);
        schedule("Alice", "Bob", 2);
        schedule("Alice", "Bob", 3);
        schedule("Alice", "Bob", 5);
        schedule("Alice", "Charlie", 2);

    }

    // implement method of calendarEntriesApiCall
    public static List<MeetingTime> calendarEntriesApiCall(String name) {
        Random rand = new Random();
        if (rand.nextFloat() < 0.25) {
            throw new RuntimeException("API call failed");
        }
        if (name.equals("Alice")){
            return List.of(new MeetingTime(8, 10), new MeetingTime(11, 12));
        }
        else if (name.equals("Bob")) {
            return List.of(new MeetingTime(9, 10));
        }
        else {
            return List.of(new MeetingTime(rand.nextInt(5) + 8, rand.nextInt(4) + 13));
        }
    }

    // implement method of createMeetingApiCall
    public static void createMeetingApiCall(List<String> names, MeetingTime meetingTime) {
        // throw new RuntimeException("API call failed") randomly 25%
        Random rand = new Random();
        if (rand.nextFloat() < 0.25) {
            throw new RuntimeException("API call failed");
        }
        System.out.println("Meeting created for " + names + " at " + meetingTime.startHour + " to " + meetingTime.endHour);
    }

    static Map<String, BigDecimal> exchangeRatesTableApiCall(String currency) { Random rand = new Random();
        if (rand.nextFloat() < 0.25) throw new RuntimeException("Connection error");
        var result = new HashMap<String, BigDecimal>();
        if(currency.equals("USD")) {
            result.put("EUR", BigDecimal.valueOf(0.81 + (rand.nextGaussian() / 100)).setScale(2, RoundingMode.FLOOR));
            result.put("JPY", BigDecimal.valueOf(103.25 + (rand.nextGaussian())).setScale(2, RoundingMode.FLOOR));
            return result;
        }
        throw new RuntimeException("Rate not available");
    }
}

// implement MeetingTime class
class MeetingTime {
    // Constructor
    public MeetingTime(int startHour, int endHour) {
        this.startHour = startHour;
        this.endHour = endHour;
    }
    // Declare the fields
    public int startHour;
    public int endHour;

    // Getters and Setters for startHour
    public int getStartHour() {
        return this.startHour;
    }

    public void setStartHour(int startHour) {
        this.startHour = startHour;
    }

    // Getters and Setters for endHour
    public int getEndHour() {
        return this.endHour;
    }

    public void setEndHour(int endHour) {
        this.endHour = endHour;
    }

}

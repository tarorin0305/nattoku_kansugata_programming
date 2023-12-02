import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class StreamExam {
    // main method
    public static void main(String[] args) {

        Stream<String> stream = Stream.of("Java", "Scala", "Groovy");

        List<String> list = stream.collect(Collectors.toList());

        for (String s : list) {
            System.out.println(s);
        }
    }
}

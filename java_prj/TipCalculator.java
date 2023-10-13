import java.util.ArrayList;
import java.util.List;

class TipCalculator {
    public static int getTipPercentage(List<String> names) {
        if(names.size() > 5) {
            return 20;
        } else if(names.size() > 0)  {
            return 10;
        } else {
            return 0;
        }
    }

    public static void main(String[] args) {
        System.out.println("Tip Calculator");

        List<String> names = new ArrayList<>();
        System.out.println(names);
        System.out.println(TipCalculator.getTipPercentage(names));

        names.add("Alice");
        names.add("Bob");
        names.add("Charlie");
        names.add("Dave");
        names.add("Eve");
        System.out.println(names);
        System.out.println(TipCalculator.getTipPercentage(names));
        names.add("Frank");
        System.out.println(names);
        System.out.println(TipCalculator.getTipPercentage(names));
    }
}

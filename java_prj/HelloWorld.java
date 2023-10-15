import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class HelloWorld {

    public static void main(String[] args) {
        List<String> words = Arrays.asList("ada", "haskell", "scala", "java", "rust");
        List<String> ranking = rankedWords(w -> score(w), words);
        System.out.println(words);
        System.out.println(ranking);
        System.out.println("----");
        List<String> ranking2 = rankedWords(w -> scoreWithBonus(w), words);
        System.out.println(ranking2);

        System.out.println("--------");
        Function<String, Integer> scoreFunction = word -> word.replace("a", "").length();
        System.out.println(scoreFunction.apply("java"));
        Function<String, Boolean> isHighScoringWordFunction = word -> scoreFunction.apply(word) > 5;
        System.out.println(isHighScoringWordFunction.apply("java"));
    }

    static Comparator<String> scoreComparator =
        (word1, word2) -> Integer.compare(score(word2), score(word1));
    static Comparator<String> scoreWithBonusComparator =
        (word1, word2) -> Integer.compare(scoreWithBonus(word2), scoreWithBonus(word1));

    public static int calculateScore(String word) {
        int score = 0;
        for (char c : word.toCharArray()) {
            if(c != 'a')
                score++;
        }

        return score;
    }

    static int score(String word) {
        return word.replaceAll("a", "").length();
    }

    static int scoreWithBonus(String word) {
        int base = score(word);
        if (word.contains("c")) {
            return base + 5;
        } else {
            return base;
        }
    }

    public static int wordScore(String word) {
        return word.replace("a", "").length();
    }

    static List<String> rankedWords(Function<String, Integer> wordScore, List<String> words) {
        Comparator<String> wordComparator =
            (word1, word2) -> Integer.compare(
                wordScore.apply(word2),
                wordScore.apply(word1)
            );
        return words
            .stream()
            .sorted(wordComparator)
            .collect(Collectors.toList());
    }

}

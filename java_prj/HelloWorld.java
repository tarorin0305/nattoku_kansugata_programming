import java.util.ArrayList;
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
        System.out.println("----1----");
        List<String> ranking2 = rankedWords(w -> scoreWithBonus(w), words);
        System.out.println(ranking2);
        System.out.println("----2----");
        List<String> ranking3 = rankedWords(w -> scoreWithPenalty(w), words);
        System.out.println(ranking3);
        System.out.println("----3----");
        List<String> ranking4 = rankedWords(w -> score(w) + bonus(w) - penalty(w), words);
        System.out.println(ranking4);
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

    static int scoreWithPenalty(String word) {
        int base = score(word);
        int bonus = word.contains("c") ? 5 : 0;
        int penalty = word.contains("s")  ? 7 : 0;
        return base + bonus - penalty;
    }

    public static int wordScore(String word) {
        return word.replace("a", "").length();
    }

    static int bonus(String word) {
        return word.contains("c") ? 5 : 0;
    }

    static int penalty(String word) {
        return word.contains("s") ? 7 : 0;
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

    // 手続き型での実装
    // static List<Integer> wordScores(
    //     Function<String, Integer> wordScore,
    //     List<String> words
    // ) {
    //     List<Integer> result = new ArrayList<>();
    //     for(String word : words) {
    //         result.add(wordScore.apply(word));
    //     }
    //     return result;
    // }

    // Streamを使った実装
    static List<Integer> wordScores(
        Function<String, Integer> wordScore,
        List<String> words
    ) {
        return words
            .stream()
            .map(wordScore)
            .collect(Collectors.toList());
    }

    static List<String> highScoringWords(
        Function<String, Integer> wordScore, List<String> words
    ) {
        List<String> result = new ArrayList<>();
        for (String word : words) {
            if (wordScore.apply(word) > 1)
                result.add(word);
        }
        return result;
    }

    static int cumulativeScore(
        Function<String, Integer> wordScore,
        List<String> words
    ) {
        int result = 0;
        for (String word : words) {
            result += wordScore.apply(word);
        }
        return result;
    }
}

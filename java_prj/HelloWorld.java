public class HelloWorld {

    public static void main(String[] args) {
        System.out.println(calculateScore("imperative"));
        System.out.println(calculateScore("declarative"));
        System.out.println(wordScore("imperative"));
        System.out.println(wordScore("declarative"));
    }

    public static int calculateScore(String word) {
        int score = 0;
        for (char c : word.toCharArray()) {
            if(c != 'a')
                score++;
        }

        return score;
    }

    public static int wordScore(String word) {
        return word.replace("a", "").length();
    }

}

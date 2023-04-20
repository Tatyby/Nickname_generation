import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {
    public static AtomicInteger count1 = new AtomicInteger();
    public static AtomicInteger count2 = new AtomicInteger();
    public static AtomicInteger count3 = new AtomicInteger();

    public static void main(String[] args) {
        Random random = new Random();
        String[] texts = new String[100_000];
        for (int i = 0; i < texts.length; i++) {
            texts[i] = generateText("abc", 3 + random.nextInt(3));
        }

        new Thread(() -> {
            for (String text : texts) {
                if (palindrome(text) && !identicalLetters(text)) {
                    count1.getAndIncrement();
                }
            }

        }).start();

        new Thread(() -> {
            for (String text : texts) {
                if (identicalLetters(text)) {
                    count2.getAndIncrement();
                }
            }
        }).start();

        new Thread(() -> {
            for (String text : texts) {
                if (lettersInAscendingOrder(text) && !identicalLetters(text)) {
                    count3.getAndIncrement();
                }
            }
        }).start();

        System.out.printf("""
                Красивых слов с длиной 3: %s шт
                Красивых слов с длиной 4: %s шт
                Красивых слов с длиной 5:  %s шт""", count1, count2, count3);

    }

    public static boolean palindrome(String str) {
        return str != null && !str.isEmpty() && str.equals(new StringBuilder(str).reverse().toString());

    }

    public static boolean identicalLetters(String str) {
        return str != null && !str.isEmpty() && str.chars().allMatch(x -> str.charAt(0) == x);
    }

    public static boolean lettersInAscendingOrder(String str) {

        if (str != null && !str.isEmpty()) {
            for (int i = 1; i < str.length(); i++) {
                if (str.charAt(i) < str.charAt(i - 1)) {
                    return false;
                }
            }
        }
        return true;
    }

    public static String generateText(String letters, int length) {
        Random random = new Random();
        StringBuilder text = new StringBuilder();
        for (int i = 0; i < length; i++) {
            text.append(letters.charAt(random.nextInt(letters.length())));
        }
        return text.toString();
    }
}
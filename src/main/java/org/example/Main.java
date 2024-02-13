package org.example;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {

    static AtomicInteger counter3 = new AtomicInteger(0);
    static AtomicInteger counter4 = new AtomicInteger(0);
    static AtomicInteger counter5 = new AtomicInteger(0);

    public static void main(String[] args) throws InterruptedException {

        Random random = new Random();
        String[] texts = new String[100_000];
        for (int i = 0; i < texts.length; i++) {
            texts[i] = generateText("abc", 3 + random.nextInt(3));
        }

        Thread thread1 = new Thread(() -> {
            checkPalindrome(texts);
        });

        Thread thread2 = new Thread(() -> {
            checkSameLetter(texts);
        });

        Thread thread3 = new Thread(() -> {
            checkLetterIncrease(texts);
        });

        thread1.start();
        thread2.start();
        thread3.start();

        thread3.join();
        thread2.join();
        thread1.join();

        System.out.println("Красивых слов с длиной 3: " + counter3.get());
        System.out.println("Красивых слов с длиной 4: " + counter4.get());
        System.out.println("Красивых слов с длиной 5: " + counter5.get());

    }

    public static String generateText(String letters, int length) {
        Random random = new Random();
        StringBuilder text = new StringBuilder();
        for (int i = 0; i < length; i++) {
            text.append(letters.charAt(random.nextInt(letters.length())));
        }
        return text.toString();
    }

    public static void addLength(String text) {
        switch (text.length()) {
            case 3 -> counter3.getAndIncrement();
            case 4 -> counter4.getAndIncrement();
            case 5 -> counter5.getAndIncrement();
        }
    }

    public static void checkPalindrome(String[] texts) {
        for (String text : texts) {
            if (text.equals(new StringBuilder(text).reverse().toString())) {
                if (!isSameLetter(text)) {
                    addLength(text);
                }
            }
        }
    }

    public static void checkSameLetter(String[] texts) {
        for (String text : texts) {
            if (isSameLetter(text)) {
                addLength(text);
            }
        }
    }

    public static void checkLetterIncrease(String[] texts) {
        for (String text : texts) {
            if (isLetterIncrease(text) && !isSameLetter(text)) {
                addLength(text);
            }
        }
    }

    public static boolean isSameLetter(String text) {
        for (int i = 0; i < text.length() - 1; i++) {
            if (text.charAt(i) != text.charAt(i + 1)) {
                return false;
            }
        }
        return true;
    }

    public static boolean isLetterIncrease(String text) {
        for (int i = 0; i < text.length() - 1; i++) {
            if (text.charAt(i) > text.charAt(i + 1)) {
                return false;
            }
        }
        return true;
    }

}
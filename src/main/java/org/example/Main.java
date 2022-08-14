package org.example;

import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;


public class Main {
    public static AtomicInteger a3 = new AtomicInteger();
    public static AtomicInteger a4 = new AtomicInteger();
    public static AtomicInteger a5 = new AtomicInteger();

    public static void main(String[] args) throws InterruptedException {
        Random random = new Random();
        String[] texts = new String[100_000];
        for (int i = 0; i < texts.length; i++) {
            texts[i] = generateText("abc", 3 + random.nextInt(3));
        }
        Thread t4 = new Thread(() -> {
            for (String s : texts) {
                if (s.length() == 4) {
                    if (s.charAt(0) == s.charAt(3) && s.charAt(1) == s.charAt(2)
                            && s.charAt(0) != s.charAt(1)) {
                        a4.incrementAndGet();
                    }
                }
            }
        });
        Thread t3 = new Thread(() -> {
            for (String s : texts) {
                if (s.length() == 3) {
                    if (s.charAt(0) == s.charAt(1) && s.charAt(1) == s.charAt(2)) {
                        a3.incrementAndGet();
                    }
                }
            }
        });
        Thread t5 = new Thread(() -> {
            for (String s : texts) {
                if (s.length() == 5) {
                    char[] d = s.toCharArray();
                    Arrays.sort(d);
                    //String f = new String(d);
                    a5.incrementAndGet();
                }
            }
        });
        t5.start();
        t4.start();
        t3.start();
        t3.join();
        t4.join();
        t5.join();
        System.out.println("Красивых слов с длиной 3 " + a3);
        System.out.println("Красивых слов с длиной 4 " + a4);
        System.out.println("Красивых слов с длиной 5 " + a5);
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
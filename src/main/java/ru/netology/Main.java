package ru.netology;

import java.util.*;
import java.util.concurrent.*;

public class Main {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        String[] texts = new String[25];
        for (int i = 0; i < texts.length; i++) {
            texts[i] = generateText("aab", 30_000);
        }

        long startTs = System.currentTimeMillis(); // start time
        List<Future> futureList = new ArrayList<>();
        int maxValue = 0;
//        final ExecutorService threadPool = Executors.newFixedThreadPool(4);

        final ExecutorService threadPool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        for (String text : texts) {

            futureList.add(threadPool.submit(new myCallable(text)));

        }
        for (Future future : futureList) {
            maxValue = Math.max((int) future.get(), maxValue);
        }
        System.out.println("maxValue = " + maxValue);

        threadPool.shutdown();

        long endTs = System.currentTimeMillis(); // end time
        System.out.println("Time: " + (endTs - startTs) + "ms");
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
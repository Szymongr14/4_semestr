package org.example;

import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    private static final int CONSUMER_COUNT = 5;
    private static final int PRODUCER_COUNT = 2;

    public static void main(String[] args) {
        ConcurrentMagazine magazine = new ConcurrentMagazine(20);

        ExecutorService consumers = Executors.newFixedThreadPool(CONSUMER_COUNT);
        for(int i=0;i<CONSUMER_COUNT;i++){
            consumers.submit(new Consumer(magazine));
        }

        ExecutorService producers = Executors.newFixedThreadPool(PRODUCER_COUNT);
        for (int i = 0; i < PRODUCER_COUNT; i++) {
            producers.submit(new Producer(magazine));
        }

        Scanner scanner = new Scanner(System.in);
        while (true) {
            String input = scanner.nextLine();
            if ("quit".equalsIgnoreCase(input)) {
                break;
            }
        }

        producers.shutdownNow();
        consumers.shutdownNow();
    }
}
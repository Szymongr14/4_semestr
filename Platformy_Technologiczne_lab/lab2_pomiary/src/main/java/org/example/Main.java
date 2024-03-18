package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        int numberOfThreads = Integer.parseInt(args[0]);
        MyBlockingQueue queue = new MyBlockingQueue(10);
        CheckIfNumberIsPrime process1 = new CheckIfNumberIsPrime(queue, new Results());
        List<Thread> threads = new ArrayList<>();

        long start = System.currentTimeMillis();

        for(int i = 0; i < numberOfThreads; i++){
            Thread thread = new Thread(process1, "Thread#" + i);
            thread.start();
            threads.add(thread); // Add the thread to the list
        }

        System.out.println("Number of working threads:" + args[0]);



        for(Thread thread : threads){
            thread.join();
        }

        long end = System.currentTimeMillis();
        System.out.println("Time taken: " + (end - start) + "ms");
    }
}
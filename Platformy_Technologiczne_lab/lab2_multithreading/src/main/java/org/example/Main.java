package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        int numberOfThreads = Integer.parseInt(args[0]);

        CheckIfNumberIsPrime process1 = new CheckIfNumberIsPrime(new MyBlockingQueue(5), new Results());
        List<Thread> threads = new ArrayList<>(); // Keep track of all threads

        for(int i = 0; i < numberOfThreads; i++){
            Thread thread = new Thread(process1, "Thread#" + i);
            thread.start();
            threads.add(thread); // Add the thread to the list
        }

        Scanner scanner = new Scanner(System.in);
        System.out.println("Number of working threads:" + args[0] + "\nType number to check if it prime. \nType 'quit' to stop app.");
        while (true) {
            String input = scanner.nextLine();
            if ("quit".equalsIgnoreCase(input)) {
                break;
            }

            try{
                int number = Integer.parseInt(input);
                process1.addNumber(number);
            }
            catch (NumberFormatException ex){
                System.out.println("Provided input is not allowed!");
            }
        }


        for (Thread thread : threads) {
            thread.interrupt(); // Interrupt the thread
        }

        scanner.close();
        process1.print_results();
    }
}
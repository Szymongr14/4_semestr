package org.example;

import static java.lang.Thread.currentThread;
import static java.lang.Thread.sleep;

public class CheckIfNumberIsPrime implements Runnable{
    private MyBlockingQueue queue;
    private Results results;
    private boolean exit_program = false;

    public CheckIfNumberIsPrime(MyBlockingQueue queue, Results results) {
        this.queue = queue;
        this.results = results;
    }

    public void addNumber(int number){
        queue.add(number);
    }


    @Override
    public void run() {
        while (!exit_program) {
            Integer number = queue.take();
            if(number == null){
                System.out.println(currentThread().getName()+ "interrupted");
                break;
            }


            try {
                Thread.sleep(2000);
            } catch (InterruptedException e){
                System.out.println(currentThread().getName()+ "interrupted");
                break;
            }

            boolean isPrime = true;
            if (number < 2) {
                isPrime = false;
            } else {
                for (int i = 2; i <= number - 1; i++) {
                    if (number % i == 0) {
                        isPrime = false;
                        break;
                    }
                }
            }
            results.addResult(number, isPrime);
            System.out.println("Result for " + number + " is: " + isPrime);
        }
        System.out.println(currentThread().getName() + " is terminated");
    }

    public synchronized void stop(){

        exit_program = true;
    }


    public void print_results(){
        results.printResults();
    }
}

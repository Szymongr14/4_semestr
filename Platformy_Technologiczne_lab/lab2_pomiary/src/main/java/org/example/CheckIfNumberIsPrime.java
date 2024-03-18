package org.example;

import static java.lang.Thread.currentThread;
import static java.lang.Thread.sleep;

public class CheckIfNumberIsPrime implements Runnable{
    private MyBlockingQueue queue;
    public Results results;
    private boolean exit_program = false;

    public CheckIfNumberIsPrime(MyBlockingQueue queue, Results results) {
        this.queue = queue;
        this.results = results;
    }

    @Override
    public void run() {
        while (results.size() < 5 ){

            int n = 500;
            int[][] firstMatrix = new int[n][n];
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    firstMatrix[i][j] = (int) (Math.random() * 1000); // Fill with random values between 0 and 9
                }
            }

            // Initialize secondMatrix with random values
            int[][] secondMatrix = new int[n][n];
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    secondMatrix[i][j] = (int) (Math.random() * 1000); // Fill with random values between 0 and 9
                }
            }

            // Example of matrix multiplication (assuming dimensions are compatible)
            int[][] resultMatrix = new int[n][n];
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    for (int k = 0; k < n; k++) {
                        resultMatrix[i][j] += firstMatrix[i][k] * secondMatrix[k][j];
                    }
                }
            }

            Matrix matrix = new Matrix(resultMatrix);
            System.out.println("done");
            results.addResult(matrix);
        }
        System.out.println(currentThread().getName() + " is terminated");
    }

    public synchronized void stop(){
        exit_program = true;
    }
}

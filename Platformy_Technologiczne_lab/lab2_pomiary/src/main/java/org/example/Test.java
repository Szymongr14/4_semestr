package org.example;

public class Test {
    public static void main(String[] args) {
        for(int i = 0; i < 50; i++){
            Test test = new Test();
            test.multiply(500,500);
        }
    }

    public void multiply(Integer x, Integer y){
        int r1 = x, c1 = y; // Dimensions for firstMatrix
        int r2 = x, c2 = y; // Dimensions for secondMatrix

        // Initialize firstMatrix with random values
        int[][] firstMatrix = new int[r1][c1];
        for (int i = 0; i < r1; i++) {
            for (int j = 0; j < c1; j++) {
                firstMatrix[i][j] = (int) (Math.random() * 1000); // Fill with random values between 0 and 9
            }
        }

        // Initialize secondMatrix with random values
        int[][] secondMatrix = new int[r2][c2];
        for (int i = 0; i < r2; i++) {
            for (int j = 0; j < c2; j++) {
                secondMatrix[i][j] = (int) (Math.random() * 1000); // Fill with random values between 0 and 9
            }
        }

        // Example of accessing and modifying elements in the matrices
        System.out.println("First matrix element at (0, 0): " + firstMatrix[0][0]);
        System.out.println("Second matrix element at (0, 0): " + secondMatrix[0][0]);

        // Example of matrix multiplication (assuming dimensions are compatible)
        int[][] resultMatrix = new int[r1][c2];
        for (int i = 0; i < r1; i++) {
            for (int j = 0; j < c2; j++) {
                for (int k = 0; k < c1; k++) {
                    resultMatrix[i][j] += firstMatrix[i][k] * secondMatrix[k][j];
                }
            }
        }

        // Print the result matrix (only a part of it for brevity)
        System.out.println("Result matrix:");
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                System.out.print(resultMatrix[i][j] + " ");
            }
            System.out.println();
        }
    }
}

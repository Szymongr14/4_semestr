package org.example;


public class Matrix {
    private Integer n;
    private int[][] matrix;

    public Matrix(Integer n) {
        this.n = n;

        int[][] firstMatrix = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                firstMatrix[i][j] = (int) (Math.random() * 1000);
            }
        }
        this.matrix = firstMatrix;
    }


    public Matrix(int[][] matrix){
        this.matrix = matrix;
        this.n = matrix.length;
    }

}

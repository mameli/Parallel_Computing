package com.github.mameli;

/**
 * Created by mameli on 02/11/2016.
 * Classe moltiplicatore matrice vettore
 */
class Multiplier implements Runnable{
    private int[][] matrix;
    private int[] vector;
    private int[] vectorResult;
    private int row;
    private int nCol;

    Multiplier(int[][] matrix, int[] vector, int[] vectorResult, int row, int nCol) {
        this.matrix = matrix;
        this.vector = vector;
        this.vectorResult = vectorResult;
        this.row = row;
        this.nCol = nCol;
    }

    @Override
    public void run() {
        int res = 0;
        for (int i = 0; i < nCol; i++) {
            res += matrix[row][i] * vector [i];
        }
        vectorResult[row] = res;
    }
}
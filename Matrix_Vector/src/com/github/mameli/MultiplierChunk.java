package com.github.mameli;

/**
 * Created by mameli on 02/11/2016.
 * Classe moltiplicazione matrice vettori a chunk
 */
class MultiplierChunk implements Runnable{
    private int[][] matrix;
    private int[] vector;
    private int[] vectorResult;
    private int row;
    private int nCol;

    MultiplierChunk(int[][] matrix, int[] vector, int[] vectorResult, int row, int nCol) {
        this.matrix = matrix;
        this.vector = vector;
        this.vectorResult = vectorResult;
        this.row = row;
        this.nCol = nCol;
    }

    @Override
    public void run() {
        int a,s,d,f;
        a = 0;
        s = 0;
        d = 0;
        f = 0;
        for (int i = 0; i < nCol; i++) {
            a+= matrix[row][i] * vector [i];
            s+= matrix[row+1][i] * vector [i];
            d+= matrix[row+2][i] * vector [i];
            f+= matrix[row+3][i] * vector [i];
        }
        vectorResult[row] = a;
        vectorResult[row+1] = s;
        vectorResult[row+2] = d;
        vectorResult[row+3] = f;
    }
}
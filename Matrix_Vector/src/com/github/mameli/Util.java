package com.github.mameli;

/**
 * Created by mameli on 02/11/2016.
 * classe utilita
 */
public class Util {

     void printVector(int[] vector){
         for (int i: vector) {
             System.out.println(vector[i]);
         }
    }

     void printMatrix(int[][] matrix,int nCols,int rows){
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < nCols; j++) {
                System.out.print(matrix[i][j]+"\t");
            }
            System.out.println();
        }
    }

     int[][] createMatrixR(int i,int j){
        int[][] matrix = new int[i][j];
        for (int k = 0; k < i; k++) {
            for (int l = 0; l < j; l++) {
                //matrix[k][l] = (int)(Math.random()*10);
                matrix[k][l] = l+k;
            }
        }
        return matrix;
    }

     int[] createVector1(int c){
        int [] vector = new int [c];
        for (int j = 0; j < c; j++) {
//           vector[j] = (int)(Math.random()*10);
            vector[j] = 1;
        }

        return vector;
    }

     boolean verifyRes(int[] seq, int [] par){
        for (int i = 0; i < seq.length; i++) {
            if (seq[i] != par[i]) return false;
        }
        return true;
    }
}

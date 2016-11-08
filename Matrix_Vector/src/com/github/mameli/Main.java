package com.github.mameli;

import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by mameli on 02/11/2016.
 * Main parallelo normale
 */

public class Main{

    public static void main(String[] args) {

        int threadPoolSize = Runtime.getRuntime().availableProcessors();
        System.out.println("Numero di processori "+ threadPoolSize);

        multiply(16,8);
        multiply(8,8000000);
        multiply(8000,8000);
        multiply(8000000,8);

    }

    private static void multiply(int nRows , int nCols){

        Util util = new Util();
        int[][] matrix = util.createMatrixR(nRows,nCols);
        int[] vector = util.createVector1(nCols);
        int[] vectorResultSeq = new int[nRows];
        int[] vectorResultPar = new int[nRows];
        long startTimePar;
        long endTimePar;


        int threadPoolSize = Runtime.getRuntime().availableProcessors();

        System.out.println("Caso "+ nRows +" x " + nCols);
        long startTimeSeq = System.currentTimeMillis();
        for (int i = 0; i < nRows; i++) {
            vectorResultSeq[i]=0;
            for (int j = 0; j < nCols; j++) {
                vectorResultSeq[i] += matrix[i][j] * vector [j];
            }
        }
        long endTimeSeq = System.currentTimeMillis();
        System.out.println("Tempo di esecuzione sequenziale " + (endTimeSeq-startTimeSeq)+" ms");

        ExecutorService executor = Executors.newFixedThreadPool(threadPoolSize);

        startTimePar = System.currentTimeMillis();
        for (int i = 0; i < nRows; i=i+4) {
//            executor.execute(new Multiplier(matrix,vector,vectorResultPar,i,nCols));
            executor.execute(new MultiplierChunk(matrix,vector,vectorResultPar,i,nCols));
        }
        executor.shutdown();
        long time = System.currentTimeMillis();
        try{
            executor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
            endTimePar = System.currentTimeMillis();
            System.out.println("Tempo di esecuzione parallelo " + (endTimePar-startTimePar)+"ms");
            System.out.println("Tempo di chiusura dei thread " + (endTimePar-time)+"ms");
            System.out.println(util.verifyRes(vectorResultSeq, vectorResultPar));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}


package core;

import task.Buffer;
import task.Consumer;
import task.Producer;

import java.util.concurrent.*;

/**
 * Main class of the example
 */
public class Main {

    /*
     * Main method of the example
     *
     */
    public static void main(String[] args) throws InterruptedException {

        /*
         * Creates a buffer with a maximum of n lines
         */
        Buffer buffer = new Buffer(3);
        int cores = Runtime.getRuntime().availableProcessors();

        System.out.println("Number of Cores and threads: "+cores);

        /*
         * Creates a producer with args or a default value and a thread to run it
         */
        Producer producer;
        if (args != null && args.length == 1)
            producer = new Producer(args[0], buffer);
        else
            producer = new Producer("Input",buffer);
        Thread threadProducer = new Thread(producer, "Producer");

        /*
         * Creates three consumers and threads to run them
         */
        Consumer consumers[] = new Consumer[cores];

        for (int i = 0; i < consumers.length; i++) {
            consumers[i] = new Consumer(buffer);
        }

        /*
         * Starts the producer and the consumers
         */
        ExecutorService exec = Executors.newFixedThreadPool(cores);
        for (Consumer consumer : consumers) {
            exec.execute(consumer);
        }
        threadProducer.start();
        threadProducer.join();
        exec.shutdown();
    }

}

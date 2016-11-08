package task;


import java.util.HashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * This class implements a buffer to stores files between the
 * producer and the consumers
 */
public class Buffer {

    /**
     * The buffer
     */
    private ConcurrentLinkedQueue<HashMap<String, String>> buffer;

    /**
     * Size of the buffer
     */
    private int maxSize;

    /**
     * Lock to control the access to the buffer
     */
    private ReentrantLock lock;

    /**
     * Conditions to control that the buffer has files and has empty space
     */
    private Condition files;
    private Condition space;

    /**
     * Attribute to control where are pending lines in the buffer
     */
    private boolean pendingFiles;

    /**
     * Constructor of the class. Initialize all the objects
     *
     * @param maxSize The size of the buffer
     */
    public Buffer(int maxSize) {
        this.maxSize = maxSize;
        buffer = new ConcurrentLinkedQueue<>();
        lock = new ReentrantLock();
        files = lock.newCondition();
        space = lock.newCondition();
        pendingFiles = true;
    }

    /**
     * Insert a file in the buffer
     *
     * @param file file to insert in the buffer
     */
    void insert(HashMap<String, String> file) {
        lock.lock();
        try {
            while (buffer.size() == maxSize) {
                space.await();
            }
            buffer.offer(file);
            System.out.printf("%s: Inserted File: %d\n", Thread.currentThread()
                    .getName(), buffer.size());
            files.signalAll();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    /**
     * Returns a file from the buffer
     *
     * @return a file from the buffer
     */
    HashMap<String, String> get() {
        HashMap<String, String> file = new HashMap<>();
        lock.lock();
        try {
            while ((buffer.size() == 0) && (hasPendingFiles())) {
                files.await();
            }

            if (hasPendingFiles()) {
                file = buffer.poll();
                System.out.printf("%s: File read: %d\n", Thread.currentThread().getName(), buffer.size());
                space.signalAll();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
        return file;
    }

    /**
     * Establish the value of the variable
     */
    void setPendingFiles(boolean pendingFiles) {
        this.pendingFiles = pendingFiles;
    }

    /**
     * Returns the value of the variable
     *
     * @return the value of the variable
     */
    boolean hasPendingFiles() {
        return pendingFiles || buffer.size() > 0;
    }

}

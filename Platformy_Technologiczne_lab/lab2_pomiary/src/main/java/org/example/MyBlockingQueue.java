package org.example;
import java.util.LinkedList;

public class MyBlockingQueue {
    private LinkedList<Matrix> queue;
    private Integer maxSize = 50;

    public MyBlockingQueue(Integer maxSize) {
        this.queue = new LinkedList<Matrix>();
        this.maxSize = maxSize;
    }

    public MyBlockingQueue() {
        this.queue = new LinkedList<Matrix>();
    }

    public synchronized void add(Matrix value){
        while(queue.size() >= maxSize){
            try {
                wait();
            } catch (InterruptedException ignored) {
            }
        }

        queue.addLast(value);
        notifyAll();
    }

    public synchronized Matrix take(){
        while(queue.size() == 0){
            try {
                wait();
            } catch (InterruptedException e) {
                return null;
            }
        }

        Matrix value = queue.removeFirst();
        notifyAll();
        return value;
    }
}

package org.example;
import java.util.LinkedList;

public class MyBlockingQueue {
    private LinkedList<Integer> queue;
    private Integer maxSize = 10;

    public MyBlockingQueue(Integer maxSize) {
        this.queue = new LinkedList<Integer>();
        this.maxSize = maxSize;
    }

    public MyBlockingQueue() {
        this.queue = new LinkedList<Integer>();
    }

    public synchronized void add(Integer value){
        while(queue.size() >= maxSize){
            try {
                wait();
            } catch (InterruptedException ignored) {
            }
        }

        queue.addLast(value);
        notifyAll();
    }

    public synchronized Integer take(){
        while(queue.size() == 0){
            try {
                wait();
            } catch (InterruptedException e) {
                return null;
            }
        }

        int value = queue.removeFirst();
        notifyAll();
        return value;
    }
}

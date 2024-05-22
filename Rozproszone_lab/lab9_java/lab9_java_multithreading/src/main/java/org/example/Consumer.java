package org.example;

public class Consumer implements Runnable{
    private final ConcurrentMagazine magazine;

    Consumer(ConcurrentMagazine magazine){
        this.magazine = magazine;
    }

    @Override
    public void run() {
        while(true) {
            int rand = (int) (Math.random() * 10) % 2;
            int rand_time = (int) (Math.random() * 10) * 1000;
            switch (rand) {
                case 0:
                    if(magazine.consume("shoes") < 0) return;
                    break;
                case 1:
                    if(magazine.consume("shirt") < 0) return;
                    break;
                default:
                    break;
            }
            try {
                System.out.printf("Thread %d timeout: %dms\n",Thread.currentThread().threadId(), rand_time);
                Thread.sleep(rand_time);
            } catch (InterruptedException e) {
                break;
            }
        }
    }
}

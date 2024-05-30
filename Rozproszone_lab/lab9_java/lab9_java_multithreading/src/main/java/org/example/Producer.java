package org.example;

public class Producer implements Runnable{
    private final ConcurrentMagazine magazine;

    Producer(ConcurrentMagazine magazine){
        this.magazine = magazine;
    }

    @Override
    public void run() {
        while(true) {
            int rand = (int) (Math.random() * 10) % 2;
            int rand_time = (int) (Math.random() * 10) * 1000;
            switch (rand) {
                case 0:
                    Product shoes = new Shoes(40);
                    if(magazine.produce(shoes) < 0) return;
                    break;
                case 1:
                    Product shirt = new Shirt("M");
                    if(magazine.produce(shirt) < 0) return;
                    break;
                default:
                    break;
            }
            try {
                System.out.printf("Producer thread %d timeout: %dms\n",Thread.currentThread().threadId(), rand_time);
                Thread.sleep(rand_time);
            } catch (InterruptedException e) {
                break;
            }
        }
    }
}

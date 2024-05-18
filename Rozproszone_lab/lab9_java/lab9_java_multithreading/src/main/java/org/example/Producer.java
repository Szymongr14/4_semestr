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
                    magazine.produce(shoes);
                    break;
                case 1:
                    Product shirt = new Shirt("M");
                    magazine.produce(shirt);
                    break;
                default:
                    break;
            }
            try {
                Thread.sleep(rand_time);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public ConcurrentMagazine getMagazine() {
        return magazine;
    }
}

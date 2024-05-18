package org.example;

public class Consumer implements Runnable{
    private final ConcurrentMagazine magazine;

    Consumer(ConcurrentMagazine magazine){
        this.magazine = magazine;
    }

    @Override
    public void run() {
        int rand = (int) (Math.random() * 10)%2;
        switch (rand){
            case 0:
                magazine.consume("shoes");
                break;


        }
    }

    public ConcurrentMagazine getMagazine() {
        return magazine;
    }
}

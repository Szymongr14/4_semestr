package org.example;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class ConcurrentMagazine {
    public ArrayList<Product> products;
    public double maxWeightOfProductsKg;

    public ConcurrentMagazine(double maxWeightOfProductsKg){
        products = new ArrayList<Product>();
        this.maxWeightOfProductsKg = maxWeightOfProductsKg;
    }

    public synchronized void produce(Product product){
        while(products.size() >= maxWeightOfProductsKg){
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        products.add(product);
        System.out.println("Produced: " + Product.name);
        notifyAll();
    }

    public synchronized void consume(String type){
        while(products.isEmpty()){
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        int index = 0;
        Product product1 = products.get(index);
        if(type.equals("shoes")){
            while(product1.getClass() != Shoes.class){
                System.out.println("Consumed: " + product1.name);
            }
        }
        else if(type.equals("shirt")){
            while(product1.getClass() != Shirt.class){
                System.out.println("Consumed: " + product1.name);
            }
        }
        products.remove(index);
        notifyAll();
    }
}

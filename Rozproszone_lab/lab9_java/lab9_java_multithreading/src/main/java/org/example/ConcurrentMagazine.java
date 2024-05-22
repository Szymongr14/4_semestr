package org.example;

import java.util.ArrayList;

public class ConcurrentMagazine {
    public ArrayList<Product> products;
    public double maxWeightOfProductsKg;

    public ConcurrentMagazine(double maxWeightOfProductsKg){
        products = new ArrayList<Product>();
        this.maxWeightOfProductsKg = maxWeightOfProductsKg;
    }

    public synchronized int produce(Product product){
        while(products.size() >= maxWeightOfProductsKg){
            try {
                System.out.println("Thread blocked");
                wait();
            } catch (InterruptedException e) {
                return -1;
            }
        }
        products.add(product);
        System.out.println("Produced: " + Product.name);
        notifyAll();
        return 0;
    }

    public synchronized int consume(String type){
        while(products.isEmpty() || !containsType(type)){
            try {
                wait();
            } catch (InterruptedException e) {
                return -1;
            }
        }
        int index = 0;
        Product product1 = products.get(index);
        if(type.equals("shoes")){
            while(product1.getClass() != Shoes.class){
                index++;
                product1 = products.get(index);
            }
            System.out.println("Consumed: " + product1.name);
        }
        else if(type.equals("shirt")){
            while(product1.getClass() != Shirt.class){
                index++;
                product1 = products.get(index);
            }
            System.out.println("Consumed: " + product1.name);
        }
        products.remove(index);
        notifyAll();
        return 0;
    }


    private boolean containsType(String type) {
        for (Product product : products) {
            if (type.equals("shoes") && product instanceof Shoes) {
                return true;
            } else if (type.equals("shirt") && product instanceof Shirt) {
                return true;
            }
        }
        return false;
    }
}

package org.example;

public class Shoes extends Product {
    public static int size;
    Shoes(int size){
        super("shoes", size * 0.085);
        Shoes.size = size;
    }
}

package org.example;

public class Shirt extends Product{
    public static String size;

    Shirt(String size){
        super("shirt", 0.5);
        Shirt.size = size;
    }
}

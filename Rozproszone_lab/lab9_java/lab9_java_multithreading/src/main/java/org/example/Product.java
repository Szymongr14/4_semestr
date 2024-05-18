package org.example;

public abstract class Product {
    public static String name;
    public static double weightKg;

    Product(String name, double weightKg){
        Product.name = name;
        Product.weightKg = weightKg;
    }
}

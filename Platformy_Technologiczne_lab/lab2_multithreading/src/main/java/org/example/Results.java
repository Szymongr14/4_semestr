package org.example;

import java.util.HashMap;

public class Results {
    private HashMap<Integer, Boolean> results;

    public Results(){
        this.results = new HashMap<>();
    }

    public synchronized void addResult(Integer primeNumber, Boolean isPrime){
        results.put(primeNumber, isPrime);
        System.out.println("Added result: " + primeNumber + " is prime: " + isPrime);
    }

    public void printResults(){
        System.out.println("Results: ");
        for (Integer key : results.keySet()) {
            System.out.println(key + " is prime: " + results.get(key));
        }
    }

}

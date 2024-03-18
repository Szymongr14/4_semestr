package org.example;

import java.util.LinkedList;
import java.util.List;

public class Results {
    private List<Matrix> results;

    public Results(){
        this.results = new LinkedList<>();
    }

    public synchronized void addResult(Matrix result){
        results.add(result);
    }

    public int size() {
        return results.size();
    }
}

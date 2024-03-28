package org.example;

import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;

public class MageRepository {
    private final Collection<Mage> collection;

    public MageRepository() {
        this.collection = new HashSet<>();
    }

    public Optional<Mage> find(String name){
        boolean exists = collection.stream().anyMatch(mage -> mage.getName().equals(name));
        if(exists){
            return Optional.of(collection.stream().filter(mage -> mage.getName().equals(name)).findFirst().get());
        }
        return Optional.empty();
    }

    public void delete(String name){
        boolean exists = collection.stream().anyMatch(mage -> mage.getName().equals(name));
        if(!exists){
            throw new IllegalArgumentException("");
        }
        collection.remove(collection.stream().filter(mage -> mage.getName().equals(name)).findFirst().get());
    }

    public void save(Mage mage) throws IllegalAccessException {
        if(collection.contains(mage)){
            throw new IllegalAccessException("");
        }
        collection.add(mage);
    }
}

package org.example;

import java.util.Collection;

public class MageController {

    private MageRepository mageRepository;

    public MageController(MageRepository mageRepository){
        this.mageRepository = mageRepository;
    }

    public String find(String name){
        Mage tempMage = mageRepository.find(name).orElse(null);
        if(tempMage == null){
            return "not found";
        }

        return tempMage.toString();
    }

    public String delete(String name){
        try{
            mageRepository.delete(name);
        }catch (IllegalArgumentException e){
//            e.printStackTrace();
            return "bad request";
        }

        return "done";
    }

    public String save(String name, String level){
        Mage newMage = new Mage(name, Integer.parseInt(level));

        try{
            mageRepository.save(newMage);
        } catch (IllegalAccessException e) {
//            e.printStackTrace();
            return "bad request";
        }
        return "done";
    }
}

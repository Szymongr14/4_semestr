package org.example;

public class Main {
    public static void main(String[] args) {
        MageRepository mageRepository = new MageRepository();
        MageController mageController = new MageController(mageRepository);

        System.out.println(mageController.save("John", "10"));
        System.out.println(mageController.save("John", "10"));
        System.out.println(mageController.save("Gino", "10"));
        System.out.println(mageController.save("Steve", "10"));
        System.out.println(mageController.save("Elon", "10"));

        System.out.println(mageController.find("John"));
        System.out.println(mageController.find("Jan"));

        System.out.println(mageController.delete("John"));
        System.out.println(mageController.delete("ELON"));

        System.out.println(mageController.find("John"));



    }

}

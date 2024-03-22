package org.example;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class Main {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("PlatformyTechnologiczneLab4PersistenceUnit");
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();

        Department department = new Department("Computer Science", 1);
        Student student = new Student("John", "Dom", department);

        // Step 5: Persist the instances
        em.persist(department);
        em.persist(student);

        // Step 6: Commit the transaction
        em.getTransaction().commit();

        em.close();
        emf.close();


    }
}
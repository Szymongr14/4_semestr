package org.example;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.Query;

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

        Query query = em.createQuery("SELECT s FROM Student s");
        System.out.println("test");
//        System.out.println(query.getResultList());
        for (Object o : query.getResultList()) {
            Student s = (Student) o;
            System.out.println(s.getName());
        }

        em.close();
        emf.close();


    }
}
package org.example;

import jakarta.persistence.*;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("PlatformyTechnologiczneLab4PersistenceUnit");
        EntityManager em = emf.createEntityManager();
        FillWithTestData(em);

        Scanner scan = new Scanner(System.in);
        String input;
        boolean running = true;
        do{
            PrintMenu();
            input = scan.nextLine();
            switch (input) {
                case "1" -> {
                    DisplayAllDepartments(em);
                    DisplayAllStudents(em);
                }
                case "2" -> AddNewStudent(em);
                case "3" -> AddNewDepartment(em);
                case "4" -> RemoveStudent(em);
                case "5" -> RemoveDepartment(em);
                case "6" -> CustomQuery1(em);
                case "7" -> running = false;
                default -> System.out.println("Incorrect input!");
            }
        }while(running);

        em.close();
        emf.close();
    }

    public static void PrintMenu(){
        System.out.println("1. Display all data");
        System.out.println("2. Add new student");
        System.out.println("3. Add new department");
        System.out.println("4. Remove student" );
        System.out.println("5. Remove department");
        System.out.println("6. Display buildings with greater id");
        System.out.println("7. Exit");
        System.out.print("Choose an option: ");
    }

    public static void CustomQuery(EntityManager em){
        System.out.println("Write name of student: ");
        Scanner scan = new Scanner(System.in);
        String name = scan.nextLine();
        Query query = em.createQuery("SELECT s FROM Student s WHERE s.firstName = :name");
        query.setParameter("name", name);
        System.out.println("Students with name " + name + ":");
        for (Object o : query.getResultList()) {
            System.out.println(o);
        }
    }

    public static void CustomQuery1(EntityManager em){
        System.out.println("Write building id: ");
        Scanner scan = new Scanner(System.in);
        int buildingId = scan.nextInt();
        Query query = em.createQuery("SELECT d FROM Department d WHERE d.buildingId >= :buildingId");
        query.setParameter("buildingId", buildingId);
        System.out.println("Departments with building id " + buildingId + ":");
        for (Object o : query.getResultList()) {
            System.out.println(o);
        }
    }

    public static void FillWithTestData(EntityManager em) {
        em.getTransaction().begin();
        Department department = new Department("Department of Computer Architecture", 1);
        Department department1 = new Department("Department of Decision Systems and Robotics", 2);
        Department department2 = new Department("Department of Geo-informatics", 3);
        Student student = new Student("John", "Black", department);
        Student student1 = new Student("Marry", "Johns", department1);
        Student student2 = new Student("Hugo", "Enis", department);
        Student student3 = new Student("Alice", "Smith", department1);
        Student student4 = new Student("Bob", "Johnson", department2);
        Student student5 = new Student("Charlie", "Williams", department2);
        em.persist(department);
        em.persist(department1);
        em.persist(department2);
        em.persist(student);
        em.persist(student1);
        em.persist(student2);
        em.persist(student3);
        em.persist(student4);
        em.persist(student5);
        em.getTransaction().commit();
    }

    public static void DisplayAllStudents(EntityManager em){
        System.out.println();
        Query query = em.createQuery("SELECT s FROM Student s");
        System.out.println("Students:");
        for (Object o : query.getResultList()) {
            System.out.println(o);
        }
        System.out.println();
    }


    public static void DisplayAllDepartments(EntityManager em){
        System.out.println();
        Query query = em.createQuery("SELECT d FROM Department d");
        System.out.println("Departments:");
        for (Object o : query.getResultList()) {
            System.out.println(o);
        }
        System.out.println();
    }

    public static void AddNewDepartment(EntityManager em){
        Scanner scan = new Scanner(System.in);
        System.out.print("Enter department's name: ");
        String name = scan.nextLine();
        System.out.print("Enter department's building id: ");
        int buildingId = scan.nextInt();
        Department department = new Department(name, buildingId);
        em.getTransaction().begin();
        em.persist(department);
        em.getTransaction().commit();
        System.out.println("Department successfully added!");
    }

    public static void AddNewStudent(EntityManager em){
        Scanner scan = new Scanner(System.in);
        System.out.print("Enter student's first name: ");
        String firstName = scan.nextLine();
        System.out.print("Enter student's last name: ");
        String lastName = scan.nextLine();
        Department choosenDepartment = ChooseDepartmentMenu(em);

        Query query = em.createQuery("SELECT d FROM Department d WHERE d.name = :name");
        query.setParameter("name", choosenDepartment.getName());
        Department department = (Department) query.getSingleResult();
        Student student = new Student(firstName, lastName, department);
        em.getTransaction().begin();
        em.persist(student);
        em.getTransaction().commit();
        System.out.println("Student successfully added!");
    }


    public static void RemoveStudent(EntityManager em){
        Scanner scan = new Scanner(System.in);
        System.out.print("Enter student's id: ");
        int studentId = scan.nextInt();

        Query query = em.createQuery("SELECT s FROM Student s WHERE s.studentId = :studentId");
        query.setParameter("studentId", studentId);
        Student student = (Student) query.getSingleResult();

        student.getDepartment().RemoveStudent(student);

        em.getTransaction().begin();
        em.remove(student);
        em.getTransaction().commit();

        System.out.println("Student successfully removed!");
    }

    public static void RemoveDepartment(EntityManager em){

        System.out.println("\nChoose department to delete: ");
        Department choosenDepartment = ChooseDepartmentMenu(em);

        em.getTransaction().begin();
        em.remove(choosenDepartment);
        em.getTransaction().commit();

        System.out.println("Department successfully removed!");
    }


    public static Department ChooseDepartmentMenu(EntityManager em){
        Scanner scan = new Scanner(System.in);

        Query departmentsQuery = em.createQuery("SELECT d FROM Department d");
        int departmentIndex;
        do {
            int index = 1;
            for (Object o : departmentsQuery.getResultList()) {
                System.out.println(index + ". " + ((Department) o).getName());
                index++;
            }
            System.out.print("Choose department: ");
            departmentIndex = scan.nextInt();
            if(departmentIndex > 0 && departmentIndex <= index){
                break;
            }
            System.out.println("Incorrect input!");
        }while(true);

        return (Department) departmentsQuery.getResultList().get(departmentIndex-1);

    }
}

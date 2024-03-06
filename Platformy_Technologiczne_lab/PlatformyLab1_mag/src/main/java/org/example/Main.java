package org.example;

import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");
        String programArgument = args[0];
        Set<Employee>andySubordinates;
        Set<Employee>garrySubordinates;
        Set<Employee>marthaSubordinates;

        Set<Employee> employeeSet;

        switch (programArgument) {
            case "natural":
                employeeSet = new TreeSet<>();
                andySubordinates = new TreeSet<>();
                garrySubordinates = new TreeSet<>();
                marthaSubordinates = new TreeSet<>();
                break;
            case "alternative":
                employeeSet = new TreeSet<>(new EmployeeComparator());
                andySubordinates = new TreeSet<>(new EmployeeComparator());
                garrySubordinates = new TreeSet<>(new EmployeeComparator());
                marthaSubordinates = new TreeSet<>(new EmployeeComparator());
                break;
            default:
                employeeSet = new HashSet<>();
                andySubordinates = new HashSet<>();
                garrySubordinates = new HashSet<>();
                marthaSubordinates = new HashSet<>();
                break;
        };

        Employee employee = new Employee("John", 30, 3000);
        Employee employee2 = new Employee("Ben", 25, 2400);
        Employee employee3 = new Employee("Maria", 25, 2490);
        Employee employee4 = new Employee("Arthur", 55, 2900);
        Employee employee5 = new Employee("Monica", 35, 4400);
        Employee employee6 = new Employee("Adam", 29, 4400);
        Employee employee7 = new Employee("Garry", 26, 4800);
        Employee employee8 = new Employee("Martha", 37, 3700);
        Employee employee9 = new Employee("Andy", 47, 7400);
        Employee employee10 = new Employee("Pep", 22, 2000);

        employeeSet.add(employee);
        employeeSet.add(employee2);
        employeeSet.add(employee3);
        employeeSet.add(employee4);
        employeeSet.add(employee5);
        employeeSet.add(employee6);
        employeeSet.add(employee7);
        employeeSet.add(employee8);
        employeeSet.add(employee9);
        employeeSet.add(employee10);

        andySubordinates.add(employee4);
        andySubordinates.add(employee5);
        andySubordinates.add(employee7);
        employee9.setSubordinates(andySubordinates);

        garrySubordinates.add(employee);
        garrySubordinates.add(employee2);
        garrySubordinates.add(employee3);
        garrySubordinates.add(employee8);
        employee7.setSubordinates(garrySubordinates);

        marthaSubordinates.add(employee6);
        marthaSubordinates.add(employee10);
        employee8.setSubordinates(marthaSubordinates);


        employee9.print();

//        System.out.println(employee9.getNumberOfSubordinates());
//        switch (programArgument) {
//            case "natural":
//                employeeSet.forEach(System.out::println);
//            case "alternative":
//                employeeSet.forEach(System.out::println);
//            default:
//                employeeSet.forEach(System.out::println);
//        };


        System.out.println(employee7.getStatsOfSubordinates(programArgument));

    }
}
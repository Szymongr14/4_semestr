package org.example;

import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

import static org.example.Employee.getHightOfSubordinatesTree;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");
        String programArgument = args[0];

        Set<Employee> employeeSet = switch (programArgument) {
            case "natural" -> new TreeSet<>();
            case "alternative" ->
//                //funkcja lambda
//                employeeSet = new TreeSet<>(new Comparator<Employee>() {
//                    @Override
//                    public int compare(Employee o1, Employee o2) {
//                        return 0;
//                    }
//                });
                    new TreeSet<>(new EmployeeComparator());
            default -> new HashSet<>();
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

        Set<Employee>andySubordinates = new HashSet<>();
        andySubordinates.add(employee);
        andySubordinates.add(employee2);
        andySubordinates.add(employee3);
        andySubordinates.add(employee4);
        andySubordinates.add(employee5);
        andySubordinates.add(employee6);
        andySubordinates.add(employee7);
        andySubordinates.add(employee8);
        andySubordinates.add(employee10);
        employee9.getSubordinates().addAll(andySubordinates);

        Set<Employee>garrySubordinates = new HashSet<>();
        garrySubordinates.add(employee);
        garrySubordinates.add(employee2);
        garrySubordinates.add(employee3);
        garrySubordinates.add(employee8);
//        employee7.getSubordinates().addAll(garrySubordinates);

        Set<Employee>marthaSubordinates = new HashSet<>();;
        marthaSubordinates.add(employee7);
        marthaSubordinates.add(employee10);
//        employee8.getSubordinates().addAll(marthaSubordinates);


//        System.out.println(employee9);
        System.out.println(getHightOfSubordinatesTree(employee9));
        System.out.println(getHightOfSubordinatesTree(employee));
        employee9.print();


//        for (Employee e : employeeSet) {
//            System.out.println(e);
//        }
    }
}
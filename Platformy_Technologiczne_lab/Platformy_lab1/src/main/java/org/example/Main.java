package org.example;

import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

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

        Employee employee = new Employee("John", 30, 3000, null);
        Employee employee2 = new Employee("Ben", 25, 2400, null);
        Employee employee3 = new Employee("Maria", 25, 2490, null);
        Employee employee4 = new Employee("Arthur", 55, 2900, null);
        Employee employee5 = new Employee("Monica", 35, 4400, null);
        Employee employee6 = new Employee("Adam", 29, 4400, null);
        Employee employee7 = new Employee("Garry", 26, 4800, null);
        Employee employee8 = new Employee("Martha", 37, 3700, null);
        Employee employee9 = new Employee("Andy", 47, 7400, null);
        Employee employee10 = new Employee("Pep", 22, 2000, null);

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


        //stworzenie hierarchi
    }
}
package org.example;

import java.util.Comparator;

public class EmployeeComparator implements Comparator<Employee> {

    @Override
    public int compare(Employee e1, Employee e2) {
        // Compare by name

        // If names are equal, compare by age
        int ageComparison = Integer.compare(e1.getAge(), e2.getAge());
        if (ageComparison != 0) {
            return -ageComparison;
        }
        int nameComparison = e1.getName().compareTo(e2.getName());
        if (nameComparison != 0) {
            return nameComparison;
        }
        // If names and ages are equal, compare by salary
        return -Double.compare(e1.getSalary(), e2.getSalary());

    }
}

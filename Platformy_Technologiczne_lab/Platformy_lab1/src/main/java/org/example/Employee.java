package org.example;

import java.util.Objects;
import java.util.Set;

public class Employee {

    private String name;
    private int age;
    private double salary;
    private Set<Employee> subordinates;

    public Employee(String name, int age, double salary, Set<Employee> subordinates) {
        this.name = name;
        this.age = age;
        this.salary = salary;
        this.subordinates = subordinates;
    }
    

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public Set<Employee> getSubordinates() {
        return subordinates;
    }

    public void setSubordinates(Set<Employee> subordinates) {
        this.subordinates = subordinates;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null ) return false;
        Employee employee = (Employee) o;
        if (age != employee.age) return false;
        if (Double.compare(employee.salary, salary) != 0) return false;
        if (!name.equals(employee.name)) return false;
        return Objects.equals(subordinates, employee.subordinates);
    }

    @Override
    public int hashCode() {
        int result = 7;
        result = name.hashCode();
        result = 31 * result + age;
        result = 31 * result + (int)salary;
        result = 31 * result + (subordinates != null ? subordinates.hashCode() : 0);
        return result;
    }
}

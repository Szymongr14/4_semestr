package org.example;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Employee implements Comparable<Employee>{

    private String name;
    private int age;
    private double salary;
    private Set<Employee> subordinates;

    public Employee(String name, int age, double salary) {
        this.name = name;
        this.age = age;
        this.salary = salary;
        this.subordinates = new HashSet<Employee>();
    }

    @Override
    public String toString() {
        return "Employee{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", salary=" + salary +
                '}';
    }

    public void print(){
        for(int i=0;i<getHightOfSubordinatesTree(this)+1;i++){
            System.out.print("-");
        }
        System.out.println(this);
        for(Employee e : this.getSubordinates()){
            e.print();
        }
    }

    @Override
    public int compareTo(Employee o) {
        int nameCompare = this.name.compareTo(o.name);
        if(nameCompare != 0){
            return nameCompare;
        }

        int ageCompare = Integer.compare(this.age, o.age);
        if(ageCompare != 0){
            return ageCompare;
        }

        return Double.compare(this.salary, o.salary);
    }

    static public int getHightOfSubordinatesTree(Employee employee){
        if(employee.getSubordinates().size() == 0){
            return 0;
        }
        else{
            int childrenMaxHeight = 0;
            for(Employee e : employee.getSubordinates()){
                childrenMaxHeight = Math.max(childrenMaxHeight, getHightOfSubordinatesTree(e));
            }
            return 1 + childrenMaxHeight;
        }
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

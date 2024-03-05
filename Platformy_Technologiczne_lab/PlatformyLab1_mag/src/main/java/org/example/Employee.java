package org.example;

import java.util.*;

public class Employee implements Comparable<Employee>{

    private String name;
    private int age;
    private double salary;
    private Set<Employee> subordinates;


    public Employee(String name, int age, double salary) {
        this.name = name;
        this.age = age;
        this.salary = salary;
        this.subordinates = new HashSet<>();
    }

    @Override
    public String toString() {
        return "Mage{" +
                "name='" + name + '\'' +
                ", level=" + age +
                ", power=" + salary +
                '}';
    }

    private void printHierarchy(int level, Set<Employee> printedEmployees){
        //if employee has been printed before
        if(printedEmployees.contains(this)){
            return;
        }

        printedEmployees.add(this);
        for(int i=0;i<level;i++){
            System.out.print("-");
        }
        System.out.println(this);

        for (Employee e : this.getSubordinates()) {
            e.printHierarchy(level + 1, printedEmployees);
        }
    }

    public void print(){
        printHierarchy(1, new HashSet<Employee>());
    }

    public Map<Employee,Integer> getStatsOfSubordinates(String startArgument){
        return switch (startArgument) {
            case "natural" -> getStatsOfSubordinatesNatural(this, new HashMap<>());
            case "alternative" -> getStatsOfSubordinatesAlternative(this, new TreeMap<>());
            default -> getStatsOfSubordinatesNatural(this, new HashMap<>());
        };
    }

    public Map<Employee, Integer> getStatsOfSubordinatesNatural(Employee employee, Map<Employee, Integer> SubordinatesMap){
        for (Employee e : this.getSubordinates()) {
            int numberOfSubordinates =  e.getNumberOfSubordinates();
            SubordinatesMap.put(e, numberOfSubordinates);
        }

        return SubordinatesMap;

    }

    public Map<Employee, Integer> getStatsOfSubordinatesAlternative(Employee employee, Map<Employee, Integer> SubordinatesMap){
        for (Employee e : this.getSubordinates()) {
            int numberOfSubordinates =  e.getNumberOfSubordinates();
            SubordinatesMap.put(e, numberOfSubordinates);
        }

        return SubordinatesMap;
    }


    public int getNumberOfSubordinates(){
        int count = 0;
        for (Employee subordinate : subordinates) {
            count += subordinate.getNumberOfSubordinates(); // Correctly count subordinates
        }
        return count + subordinates.size(); // Add the count of direct subordinates

    }


//    private int getNumberOfSubordinatesHierarchy(Employee employee, Set<Employee>visited){
//        if(visited.contains(employee)){
//            return 0;
//        }
//        visited.add(employee);
//        int numberOfSubordinates = 0;
//        for(Employee e : this.getSubordinates()){
//            numberOfSubordinates += 1 + e.getNumberOfSubordinatesHierarchy(employee,visited);
//        }
//        return numberOfSubordinates;
//    }

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



package org.example;

import jakarta.persistence.*;

@Entity
@Table(name="student")
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int studentId;

    private String name;

    private String surname;

    @ManyToOne
    private Department department;


    public Student(String name, String surname, Department department) {
        this.name = name;
        this.surname = surname;
        this.department = department;
    }

    public Student() {

    }

    public String getName() {
        return name;
    }
}

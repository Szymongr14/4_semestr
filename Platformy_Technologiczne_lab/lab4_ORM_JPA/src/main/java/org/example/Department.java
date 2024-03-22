package org.example;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="department")
public class Department {
    @Id
    private String name;

    private Integer buildingId;

    @OneToMany
    private List<Student> students;

    public Department(String name, Integer buildingId) {
        this.name = name;
        this.buildingId = buildingId;
        this.students = new ArrayList<>();
    }

    public Department() {

    }
}

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

    @OneToMany(mappedBy = "department")
    private List<Student> students;

    public Department(String name, Integer buildingId) {
        this.name = name;
        this.buildingId = buildingId;
        this.students = new ArrayList<>();
    }

    public Department() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getBuildingId() {
        return buildingId;
    }

    public void setBuildingId(Integer buildingId) {
        this.buildingId = buildingId;
    }

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }
}

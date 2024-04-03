package org.example;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString

@Entity
@Table(name="department")
public class Department {
    @Id
    private String name;

    private Integer buildingId;

    @OneToMany(mappedBy = "department", cascade = CascadeType.ALL)
    private List<Student> students;

    public Department(String name, Integer buildingId) {
        this.name = name;
        this.buildingId = buildingId;
        this.students = new ArrayList<>();
    }

    public void AddStudent(Student s){
        students.add(s);
    }

    public void RemoveStudent(Student s){
        students.remove(s);
    }

}

package mk.ukim.finki.wp.lab.model;

import lombok.Data;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Data
@Entity
@Table(name="students")
public class Student {
    @Id
    String username;
    String password;
    String name;
    String surname;
    @ManyToMany(mappedBy = "students", fetch = FetchType.EAGER)
    private List<Course> courses;

    public Student() {
    }

    public Student(String username, String password, String name, String surname) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.surname = surname;
    }

    //Equals by username, preserve adding students with same username (before using DB)
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return Objects.equals(username, student.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username);
    }

    @Override
    public String toString() {
        return "Student{" +
                "username='" + username + '\'' +
                '}';
    }
}

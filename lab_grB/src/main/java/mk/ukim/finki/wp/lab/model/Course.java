package mk.ukim.finki.wp.lab.model;

import lombok.Data;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;
import java.util.Random;

@Data
@Entity
@Table(name="courses") //setting table name is optional, by default is taken class' name
public class Course {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long courseId;
    private String name;
    private String description;
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Student> students;
    @OneToOne
    private Teacher teacher;
    @Enumerated(EnumType.STRING)
    private Type type;

    public Course() {
    }

    public Course(String name, String description, List<Student> students, Teacher teacher) {
        //this.courseId = (long) (Math.random()*1000);
        this.name = name;
        this.description = description;
        this.students = students;
        this.teacher = teacher;
    }

    public void setStudents(List<Student> students) {
        if(this.getStudents()!=null)
            this.students = students;
        else this.students.addAll(students);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Course course = (Course) o;
        return name.equals(course.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return "Course{" +
                "name='" + name + '\'' +
                '}';
    }
}

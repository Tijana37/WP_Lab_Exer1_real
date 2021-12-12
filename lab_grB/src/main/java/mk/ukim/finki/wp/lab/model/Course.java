package mk.ukim.finki.wp.lab.model;

import lombok.Data;

import java.util.List;
import java.util.Objects;
import java.util.Random;

@Data
public class Course {
    Long courseId;
    String name;
    String description;
    List<Student> students;
    Teacher teacher;

    public Course(String name, String description, List<Student> students) {
        this.courseId = (long) (Math.random()*1000);
        this.name = name;
        this.description = description;
        this.students = students;
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
}

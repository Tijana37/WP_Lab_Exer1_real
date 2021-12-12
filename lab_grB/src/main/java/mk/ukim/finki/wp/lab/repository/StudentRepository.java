package mk.ukim.finki.wp.lab.repository;

import mk.ukim.finki.wp.lab.bootstrap.DataHolder;
import mk.ukim.finki.wp.lab.model.Course;
import mk.ukim.finki.wp.lab.model.Exceptions.CourseIDException;
import mk.ukim.finki.wp.lab.model.Exceptions.StudentNotExist;
import mk.ukim.finki.wp.lab.model.Student;
import mk.ukim.finki.wp.lab.model.Teacher;
import org.springframework.stereotype.Repository;

import javax.xml.crypto.Data;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Repository
public class StudentRepository {

    public StudentRepository() {
    }

    public List<Student> findAllStudents(){
        return DataHolder.students;
    }

    public List<Student> findAllByNameOrSurname(String text){
        return DataHolder.students.stream().filter(s->s.getName().contains(text)||s.getSurname().contains(text)).collect(Collectors.toList());
    }
    public Optional<Student> findByUsername(String username){
        return DataHolder.students.stream().filter(s->s.getUsername().compareTo(username)==0).findFirst();
    }
    public void addStudent(String username, String password, String name, String surname) {
        Student c = new Student(username, password, name, surname);
        if (!DataHolder.students.contains(c))
            DataHolder.students.add(c);
    }

    public void deleteStudent(Student s) throws StudentNotExist {
        DataHolder.students.remove(s);
    }
}

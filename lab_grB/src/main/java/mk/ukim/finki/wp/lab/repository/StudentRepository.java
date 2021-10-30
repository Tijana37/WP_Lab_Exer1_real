package mk.ukim.finki.wp.lab.repository;

import mk.ukim.finki.wp.lab.model.Student;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Repository
public class StudentRepository {
    List<Student> students;

    public StudentRepository() {
        this.students = new ArrayList<>();
        IntStream.range(0,5).forEach(i->addStudent("Tijana"+String.valueOf(i),"Tijana"+String.valueOf(i), "Tijana","At"));
    }
    //method added by me
    public Student addStudent(String username, String password, String name, String surname){
        Student s = new Student(username,password,name,surname);
        this.students.add(s);
        return s;
    }
    public List<Student> findAllStudents(){
        return this.students;
    }
    public List<Student> findAllByNameOrSurname(String text){
        return this.students.stream().filter(s->s.getName().contains(text)||s.getSurname().contains(text)).collect(Collectors.toList());
    }

}

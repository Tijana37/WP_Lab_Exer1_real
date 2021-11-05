package mk.ukim.finki.wp.lab.repository;

import mk.ukim.finki.wp.lab.bootstrap.DataHolder;
import mk.ukim.finki.wp.lab.model.Student;
import org.springframework.stereotype.Repository;

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

}

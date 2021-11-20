package mk.ukim.finki.wp.lab.repository;

import mk.ukim.finki.wp.lab.bootstrap.DataHolder;
import mk.ukim.finki.wp.lab.model.Teacher;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.IntStream;

@Repository
public class TeacherRepository {
    public TeacherRepository() {
        DataHolder.teachers.add(new Teacher("Mark", "Markss"));
        DataHolder.teachers.add(new Teacher("John", "Jonhson"));
        DataHolder.teachers.add(new Teacher("Peter", "Peterson"));
        DataHolder.teachers.add(new Teacher("Clarice", "Blue"));
        DataHolder.teachers.add(new Teacher("Marie", "West"));

    }

    public List<Teacher> findAll() {
        return DataHolder.teachers;
    }

    public Teacher findByID(String id) {
        return DataHolder.teachers.stream().filter(t -> t.getId().equals(Long.parseLong(id))).findFirst().get();
    }
    public void deleteByID(Long id){
        Teacher toDelete = DataHolder.teachers.stream().filter(t -> t.getId().equals(id)).findFirst().get();
        DataHolder.teachers.remove(toDelete);

    }
    public void addTeacher(String name,String surname){
        DataHolder.teachers.add(new Teacher(name,surname));
    }
}
package mk.ukim.finki.wp.lab.repository;

import mk.ukim.finki.wp.lab.bootstrap.DataHolder;
import mk.ukim.finki.wp.lab.model.Teacher;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

@Repository
public class TeacherRepository {
    public TeacherRepository() {
    }

    public List<Teacher> findAll() {
        return DataHolder.teachers;
    }

    public Optional<Teacher> findByID(String id) {
        return DataHolder.teachers.stream().filter(t -> t.getId().equals(Long.parseLong(id))).findFirst();
    }

    public void deleteByID(Long id) {
        Teacher toDelete = DataHolder.teachers.stream().filter(t -> t.getId().equals(id)).findFirst().get();
        DataHolder.teachers.remove(toDelete);

    }

    public void addTeacher(String name, String surname) {
        DataHolder.teachers.add(new Teacher(name, surname));
    }
}
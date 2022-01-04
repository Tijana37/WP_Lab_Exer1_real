package mk.ukim.finki.wp.lab.repository.impl;

import mk.ukim.finki.wp.lab.bootstrap.DataHolder;
import mk.ukim.finki.wp.lab.model.Teacher;
import mk.ukim.finki.wp.lab.model.TeacherFullname;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
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

    public void addTeacher(String name, String surname, LocalDate date) {
        System.out.println("name: "+name);
        System.out.println("surname: "+surname);

        TeacherFullname teacherFullname = new TeacherFullname();
        teacherFullname.setName(name);
        teacherFullname.setSurname(surname);
        //DataHolder.teachers.add(new Teacher(name, surname));
        DataHolder.teachers.add(new Teacher(teacherFullname,date));

    }
}
package mk.ukim.finki.wp.lab.service;

import mk.ukim.finki.wp.lab.model.Exceptions.TeacherNotFound;
import mk.ukim.finki.wp.lab.model.Teacher;

import java.util.List;
import java.util.Optional;

public interface TeacherService {
    List<Teacher> findAll();
    Optional<Teacher> findByID(Long id);
    void delete(Long id) throws TeacherNotFound;
   void addTeacher(String name,String surname);
}

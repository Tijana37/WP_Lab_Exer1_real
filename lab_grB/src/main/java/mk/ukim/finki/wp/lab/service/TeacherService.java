package mk.ukim.finki.wp.lab.service;

import mk.ukim.finki.wp.lab.model.Teacher;

import java.util.List;

public interface TeacherService {
    List<Teacher> findAll();
    Teacher findByID(String id);
    void delete(Long id);
   void addTeacher(String name,String surname);
}

package mk.ukim.finki.wp.lab.service.impl;

import mk.ukim.finki.wp.lab.model.Teacher;
import mk.ukim.finki.wp.lab.repository.TeacherRepository;
import mk.ukim.finki.wp.lab.service.TeacherService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TeacherServiceImpl implements TeacherService {
    public final TeacherRepository teacherRepository;

    public TeacherServiceImpl(TeacherRepository teacherRepository) {
        this.teacherRepository = teacherRepository;
    }

    @Override
    public List<Teacher> findAll() {
        return teacherRepository.findAll();
    }

    @Override
    public Optional<Teacher> findByID(String id) {
        return teacherRepository.findAll().stream().filter(t->t.getId().equals(Long.parseLong(id))).findFirst();
    }

    @Override
    public void delete(Long id) {
            teacherRepository.deleteByID(id);
    }

    @Override
    public void addTeacher(String name, String surname) {
      teacherRepository.addTeacher(name,surname);
    }


}

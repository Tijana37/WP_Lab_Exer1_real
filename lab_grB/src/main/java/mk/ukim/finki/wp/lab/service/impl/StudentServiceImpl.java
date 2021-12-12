package mk.ukim.finki.wp.lab.service.impl;

import mk.ukim.finki.wp.lab.model.Course;
import mk.ukim.finki.wp.lab.model.Student;
import mk.ukim.finki.wp.lab.repository.CourseRepository;
import mk.ukim.finki.wp.lab.repository.StudentRepository;
import mk.ukim.finki.wp.lab.service.StudentService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class StudentServiceImpl implements StudentService {
    public final StudentRepository studentRepository;
    public final CourseRepository courseService;

    public StudentServiceImpl(StudentRepository studentRepository, CourseRepository courseService) {
        this.studentRepository = studentRepository;
        this.courseService = courseService;
    }

    @Override
    public List<Student> listAll() {
        return studentRepository.findAllStudents();
    }

    @Override
    public List<Student> searchByNameOrSurname(String text) {
        return studentRepository.findAllByNameOrSurname(text);
    }

    @Override
    public Optional<Student> searchByUsername(String text) {
        return studentRepository.findByUsername(text);
    }

    @Override
    public List<Course> getCoursesForStudent(Student s) {
        return this.courseService.findAllCourses().stream().filter(c -> c.getStudents().contains(s)).collect(Collectors.toList());
    }

    @Override
    public Student save(String username, String password, String name, String surname) {
        Student s = new Student(username, password, name, surname);
        studentRepository.findAllStudents().add(s);
        return s;
    }
}


package mk.ukim.finki.wp.lab.service;

import mk.ukim.finki.wp.lab.model.Course;
import mk.ukim.finki.wp.lab.model.Exceptions.CourseIDException;
import mk.ukim.finki.wp.lab.model.Student;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public interface CourseService{
    List<Course> listAll();
    List<Student> listStudentsByCourse(Long courseId);
    Course addStudentInCourse(String username, Long courseId);
    void addCourse(String name, String descr, String professorId);
    void deleteCourse( Long id) throws CourseIDException;
    Course getCourse(Long id);
}

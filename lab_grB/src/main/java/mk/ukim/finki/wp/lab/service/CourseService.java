package mk.ukim.finki.wp.lab.service;

import mk.ukim.finki.wp.lab.model.Course;
import mk.ukim.finki.wp.lab.model.Exceptions.CourseIDException;
import mk.ukim.finki.wp.lab.model.Exceptions.StudentNotExist;
import mk.ukim.finki.wp.lab.model.Exceptions.TeacherNotFound;
import mk.ukim.finki.wp.lab.model.Student;

import java.util.HashMap;
import java.util.List;

public interface CourseService {
    List<Course> listAll();

    List<Student> listStudentsByCourse(Long courseId) throws CourseIDException;

    Course addStudentInCourse(String username, Long courseId) throws CourseIDException, StudentNotExist;

    Course addCourse(String name, String descr, String professorId) throws TeacherNotFound;

    void deleteCourse(Long id) throws CourseIDException;

    Course getCourse(Long id) throws CourseIDException;

}

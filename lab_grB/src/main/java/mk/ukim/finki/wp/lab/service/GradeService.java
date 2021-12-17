package mk.ukim.finki.wp.lab.service;

import mk.ukim.finki.wp.lab.model.Course;
import mk.ukim.finki.wp.lab.model.Exceptions.CourseIDException;
import mk.ukim.finki.wp.lab.model.Exceptions.StudentNotExist;
import mk.ukim.finki.wp.lab.model.Grade;
import mk.ukim.finki.wp.lab.model.Student;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

public interface GradeService {
    Grade insertGrade(Long courseId, String studentUsername, Character grade, LocalDateTime dateTime) throws StudentNotExist, CourseIDException;

    HashMap<Student, Character> getGradesForStudentsInCourse(Long courseId) throws CourseIDException;

    List<Grade> findAllGrades();

    List<Grade> findGradesBetween(LocalDateTime begin, LocalDateTime end);

    HashMap<Student, Character> findGradesBetweenForCourse(LocalDateTime begin, LocalDateTime end, Long courseId);

}

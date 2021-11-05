package mk.ukim.finki.wp.lab.repository;

import mk.ukim.finki.wp.lab.bootstrap.DataHolder;
import mk.ukim.finki.wp.lab.model.Course;
import mk.ukim.finki.wp.lab.model.Student;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Repository
public class CourseRepository {

    public CourseRepository(){}

    public List<Course> findAllCourses(){
        return DataHolder.courses;
    }
    public Course findById(Long courseId){
        //when working with Long always use .equals, not ==
        return DataHolder.courses.stream().filter(c-> c.getCourseId().equals(courseId)).findFirst().get();
    }
    public List<Student> findAllStudentsByCourse(Long courseId){
        return this.findById(courseId).getStudents();
    }
    public Course addStudentToCourse(Student student, Course course){
        Student newStudent = new Student(student.getUsername(),student.getPassword(),student.getName(),student.getSurname());
        Course c = this.findById(course.getCourseId());
        c.getStudents().add(newStudent);
        return c;
    }


}

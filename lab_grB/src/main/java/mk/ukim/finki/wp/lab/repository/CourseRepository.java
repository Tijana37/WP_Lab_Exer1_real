package mk.ukim.finki.wp.lab.repository;

import mk.ukim.finki.wp.lab.model.Course;
import mk.ukim.finki.wp.lab.model.Student;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Repository
public class CourseRepository {
    List<Course> courses ;

    public CourseRepository(){
        this.courses = new ArrayList<>();
        this.addCourse(1111L, "AI","Artifical Intelligence", new StudentRepository().findAllStudents());
        this.addCourse(2222L, "ML","Machine Learning", new StudentRepository().findAllStudents());
        this.addCourse(3333L, "OS","Operating Systems", new StudentRepository().findAllStudents());
        this.addCourse(4444L, "LA","Linear Algebra", new StudentRepository().findAllStudents());
        this.addCourse(5555L, "WP","Web Programming", new StudentRepository().findAllStudents());
    }
    //method created by me
    public void addCourse(Long courseId, String name, String description, List<Student> students){
        Course c = new Course(courseId,  name,  description, students);
        this.courses.add(c);
    }
    public List<Course> findAllCourses(){
        return this.courses;
    }
    public Course findById(Long courseId){
        //when working with Long always use .equals, not ==
        return this.courses.stream().filter(c-> c.getCourseId().equals(courseId)).findFirst().get();
    }
    public List<Student> findAllStudentsByCourse(Long courseId){
        return this.findById(courseId).getStudents();
    }
    public Course addStudentToCourse(Student student, Course course){
        List<Student> newStudent = new ArrayList<>();
        newStudent.add(student);
        this.findById(course.getCourseId()).setStudents(newStudent);
        return this.findById(course.getCourseId());
    }


}

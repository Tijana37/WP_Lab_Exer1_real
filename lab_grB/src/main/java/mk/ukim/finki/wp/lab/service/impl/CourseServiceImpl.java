package mk.ukim.finki.wp.lab.service.impl;

import mk.ukim.finki.wp.lab.model.Course;
import mk.ukim.finki.wp.lab.model.Student;
import mk.ukim.finki.wp.lab.repository.CourseRepository;
import mk.ukim.finki.wp.lab.service.CourseService;
import mk.ukim.finki.wp.lab.service.StudentService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseServiceImpl implements CourseService {
    public final CourseRepository courseRepository;
    public final StudentService studentService;

    public CourseServiceImpl(CourseRepository courseRepository, StudentServiceImpl studentService) {
        this.courseRepository = courseRepository;
        this.studentService = studentService;
    }

    @Override
    public List<Course> listAll() {
        return courseRepository.findAllCourses();
    }

    @Override
    public List<Student> listStudentsByCourse(Long courseId) {
        return courseRepository.findById(courseId).getStudents();
    }

    @Override
    public Course addStudentInCourse(String username, Long courseId) {
        Student s = studentService.listAll().stream().filter(x->x.getUsername().compareTo(username)==0).findFirst().get();
        Course c = this.courseRepository.findById(courseId);
        c.getStudents().add(s);
        return c;
    }
}

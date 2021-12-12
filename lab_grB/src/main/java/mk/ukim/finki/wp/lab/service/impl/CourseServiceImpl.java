package mk.ukim.finki.wp.lab.service.impl;

import mk.ukim.finki.wp.lab.bootstrap.DataHolder;
import mk.ukim.finki.wp.lab.model.Course;
import mk.ukim.finki.wp.lab.model.Exceptions.CourseIDException;
import mk.ukim.finki.wp.lab.model.Exceptions.StudentNotExist;
import mk.ukim.finki.wp.lab.model.Exceptions.TeacherNotFound;
import mk.ukim.finki.wp.lab.model.Student;
import mk.ukim.finki.wp.lab.repository.CourseRepository;
import mk.ukim.finki.wp.lab.service.CourseService;
import mk.ukim.finki.wp.lab.service.StudentService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
    public List<Student> listStudentsByCourse(Long courseId) throws CourseIDException {
        return courseRepository.findAllStudentsByCourse(courseId);
    }

    @Override
    public Course addStudentInCourse(String username, Long courseId) throws CourseIDException, StudentNotExist {
        Optional<Student> s = studentService.searchByUsername(username);
        Optional<Course> c = this.courseRepository.findById(courseId);
        if (s.isPresent() && c.isPresent()) {
            this.courseRepository.addStudentToCourse(s.get(), c.get());
            return c.get();
        }
        if (c.isEmpty())
            throw new CourseIDException(courseId);
        else throw new StudentNotExist(username);
    }

    @Override
    public void addCourse(String name, String descr, String professorId) throws TeacherNotFound {
        courseRepository.addCourse(name, descr, professorId);
    }

    @Override
    public void deleteCourse(Long id) throws CourseIDException {
        Optional<Course> c = this.courseRepository.findById(id);
        if (c.isPresent())
            courseRepository.deleteCourse(c.get());
        else
            throw new CourseIDException(id);
    }

    @Override
    public Course getCourse(Long id) throws CourseIDException {
        Optional<Course> c = courseRepository.findById(id);
        if (c.isPresent())
            return c.get();
        else throw new CourseIDException(id);
    }


}

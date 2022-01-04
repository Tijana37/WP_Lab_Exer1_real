package mk.ukim.finki.wp.lab.service.impl;

import mk.ukim.finki.wp.lab.model.*;
import mk.ukim.finki.wp.lab.model.Exceptions.CourseIDException;
import mk.ukim.finki.wp.lab.model.Exceptions.StudentNotExist;
import mk.ukim.finki.wp.lab.model.Exceptions.TeacherNotFound;
import mk.ukim.finki.wp.lab.repository.jpa.CourseRepositoryJPA;
import mk.ukim.finki.wp.lab.repository.jpa.GradeRepositoryJPA;
import mk.ukim.finki.wp.lab.service.CourseService;
import mk.ukim.finki.wp.lab.service.StudentService;
import mk.ukim.finki.wp.lab.service.TeacherService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Service
public class CourseServiceImpl implements CourseService {
    //public final CourseRepository courseRepository;
    public final CourseRepositoryJPA courseRepository;
    public final TeacherService teacherService;
    public final StudentService studentService;
    public final GradeRepositoryJPA gradeRepository;

    public CourseServiceImpl(CourseRepositoryJPA courseRepository, TeacherService teacherService, StudentServiceImpl studentService, GradeRepositoryJPA gradeRepository) {
        this.courseRepository = courseRepository;
        this.teacherService = teacherService;
        this.studentService = studentService;
        this.gradeRepository = gradeRepository;
    }

    @Override
    public List<Course> listAll() {
        //return courseRepository.findAllCourses();
        return courseRepository.findAll();
    }

    @Override
    public List<Student> listStudentsByCourse(Long courseId) throws CourseIDException {
        if (courseRepository.existsById(courseId))
            return courseRepository.findById(courseId).get().getStudents();
        else throw new CourseIDException(courseId);
        //return courseRepository.findAllStudentsByCourse(courseId);
    }


    @Override
    public Course addStudentInCourse(String username, Long courseId) throws CourseIDException, StudentNotExist {
        Optional<Student> s = studentService.findByUsername(username);
        Optional<Course> c = this.courseRepository.findById(courseId);
        if (s.isPresent() && c.isPresent()) {
            //this.courseRepository.addStudentToCourse(s.get(), c.get());
            c.get().getStudents().add(s.get());
            //After adding new Students or editing data in Entity, you must call .save method to
            //update the database. Save is used for new entity and for updating EXISTING entity
            courseRepository.save(c.get());
            return c.get();
        }
        if (c.isEmpty())
            throw new CourseIDException(courseId);
        else throw new StudentNotExist(username);
    }

    @Override
    public Course addCourse(String name, String descr, String professorId, String type) throws TeacherNotFound {
        Optional<Teacher> t = teacherService.findByID(Long.parseLong(professorId));
        if (t.isPresent()) {
            // courseRepository.addCourse(name, descr, new ArrayList<>(),t.get());
            return courseRepository.save(new Course(name, descr, new ArrayList<>(), t.get(),Type.valueOf(type)));
        } else
            throw new TeacherNotFound(professorId);
    }

    @Override
    public void deleteCourse(Long id) throws CourseIDException {
        Optional<Course> c = this.courseRepository.findById(id);
        if (c.isPresent()) {
            //courseRepository.deleteCourse(c.get());
            for (Grade g : gradeRepository.findByCourse(c.get())) {
                gradeRepository.delete(g);
            }
            courseRepository.delete(c.get());
        } else
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

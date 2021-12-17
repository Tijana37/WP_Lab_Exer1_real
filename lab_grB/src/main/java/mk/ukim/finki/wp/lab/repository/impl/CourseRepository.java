package mk.ukim.finki.wp.lab.repository.impl;

import mk.ukim.finki.wp.lab.bootstrap.DataHolder;
import mk.ukim.finki.wp.lab.model.Course;
import mk.ukim.finki.wp.lab.model.Exceptions.CourseIDException;
import mk.ukim.finki.wp.lab.model.Exceptions.TeacherNotFound;
import mk.ukim.finki.wp.lab.model.Student;
import mk.ukim.finki.wp.lab.model.Teacher;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class CourseRepository {
    private final TeacherRepository teacherRepository;

    public CourseRepository(TeacherRepository teacherRepository) {
        this.teacherRepository = teacherRepository;
    }

    public List<Course> findAllCourses() {
        return DataHolder.courses;
    }

    public Optional<Course> findById(Long courseId) {
        //when working with Long always use .equals, not ==
        //when working with long always use ==
        return DataHolder.courses.stream().filter(c -> c.getCourseId().equals(courseId)).findFirst();
    }

    public List<Student> findAllStudentsByCourse(Long courseId) throws CourseIDException {
        Optional<Course> searchedCourse = this.findById(courseId);
        if (searchedCourse.isPresent())
            return searchedCourse.get().getStudents();
        else throw new CourseIDException(courseId);
    }

    public Course addStudentToCourse(Student student, Course course) {
        course.getStudents().add(student);
        return course;
    }

    public void addCourse(String name, String descr, String professorId) throws TeacherNotFound {
        Optional<Teacher> t = teacherRepository.findByID(professorId);
        Course c = null;
        if(t.isPresent())
            c = new Course(name, descr, new ArrayList<>(), t.get());
        else throw new TeacherNotFound(professorId);
        if (!DataHolder.courses.contains(c))
            //Will compare by EQUALS (implemented in Course) and will not allow adding courses with same name
            DataHolder.courses.add(c);
        //sort after every adding
        DataHolder.courses.sort(DataHolder.comparator);
    }

    public void deleteCourse(Course c) throws CourseIDException {
        DataHolder.courses.remove(c);
    }



}

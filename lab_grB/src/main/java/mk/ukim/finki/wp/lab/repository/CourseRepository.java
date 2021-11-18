package mk.ukim.finki.wp.lab.repository;

import mk.ukim.finki.wp.lab.bootstrap.DataHolder;
import mk.ukim.finki.wp.lab.model.Course;
import mk.ukim.finki.wp.lab.model.Exceptions.CourseIDException;
import mk.ukim.finki.wp.lab.model.Student;
import mk.ukim.finki.wp.lab.model.Teacher;
import org.springframework.stereotype.Repository;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class CourseRepository {
    private final TeacherRepository teacherRepository;

    public CourseRepository(TeacherRepository teacherRepository){
        this.teacherRepository = teacherRepository;
    }

    public List<Course> findAllCourses(){
        return DataHolder.courses;
    }
    public Course findById(Long courseId){
        //when working with Long always use .equals, not ==
        //when working with long always use ==
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

    public void addCourse(String name, String descr, String professorId) {
        Course c = new Course(name, descr, null);
        Teacher t = teacherRepository.findByID(professorId);
        c.setTeacher(t);
        if(!DataHolder.courses.contains(c)) //Will compare by EQUALS and will not allow adding courses with same name
            DataHolder.courses.add(c);
        //sort after every adding
        DataHolder.courses.sort(DataHolder.comparator);
    }

    public void deleteCourse(Long id) throws CourseIDException {
        Course c = DataHolder.courses.stream().filter(i->i.getCourseId().equals(id)).findFirst().get();
        if(c==null)
            throw new CourseIDException(id);
        DataHolder.courses.remove(c);
    }


}

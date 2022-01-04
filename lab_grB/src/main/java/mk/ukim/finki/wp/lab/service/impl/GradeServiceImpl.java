package mk.ukim.finki.wp.lab.service.impl;

import mk.ukim.finki.wp.lab.model.Course;
import mk.ukim.finki.wp.lab.model.Exceptions.CourseIDException;
import mk.ukim.finki.wp.lab.model.Exceptions.StudentNotExist;
import mk.ukim.finki.wp.lab.model.Grade;
import mk.ukim.finki.wp.lab.model.Student;
import mk.ukim.finki.wp.lab.repository.jpa.CourseRepositoryJPA;
import mk.ukim.finki.wp.lab.repository.jpa.GradeRepositoryJPA;
import mk.ukim.finki.wp.lab.repository.jpa.StudentRepositoryJPA;
import mk.ukim.finki.wp.lab.service.GradeService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Service
public class GradeServiceImpl implements GradeService {
    private final GradeRepositoryJPA gradeRepositoryJPA;
    private final StudentRepositoryJPA studentRepositoryJPA;
    private final CourseRepositoryJPA courseRepositoryJPA;


    public GradeServiceImpl(GradeRepositoryJPA gradeRepositoryJPA, StudentRepositoryJPA studentRepositoryJPA, CourseRepositoryJPA courseRepositoryJPA) {
        this.gradeRepositoryJPA = gradeRepositoryJPA;
        this.studentRepositoryJPA = studentRepositoryJPA;
        this.courseRepositoryJPA = courseRepositoryJPA;
    }


    @Override
    public Grade insertGrade(Long courseId, String studentUsername, Character grade, LocalDateTime dateTime) throws StudentNotExist, CourseIDException {
        Optional<Student> s = studentRepositoryJPA.findByUsername(studentUsername);
        Optional<Course> c = courseRepositoryJPA.findById(courseId);
        if (s.isPresent() && c.isPresent())
            return gradeRepositoryJPA.save(new Grade(grade, s.get(), c.get(), dateTime));
        else {
            if (s.isEmpty())
                throw new StudentNotExist(studentUsername);
            else throw new CourseIDException(courseId);
        }
    }

    @Override
    public HashMap<Student, Character> getGradesForStudentsInCourse(Long courseId) throws CourseIDException {
        HashMap<Student, Character> result = new HashMap<>();
        Optional<Course> c = courseRepositoryJPA.findById(courseId);
        if (c.isPresent()) {
            List<Grade> grades = gradeRepositoryJPA.findByCourse(c.get());
            for (Grade g : grades) {
                result.put(g.getStudent(), g.getGrade());
            }
        }
        return result;
    }

    @Override
    public Iterable<Grade> findAllGrades() {
        Pageable firstPageWithTwoElements = PageRequest.of(0, 2);
        Page<Grade> result = gradeRepositoryJPA.findAll(firstPageWithTwoElements);
        //return   result.get().collect(Collectors.toList());
        return result;

    }

    @Override
    public List<Grade> findGradesBetween(LocalDateTime begin, LocalDateTime end) {
        return gradeRepositoryJPA.findByTimestampBetween(begin, end);
    }

    @Override
    public HashMap<Student, Character> findGradesBetweenForCourse(LocalDateTime begin, LocalDateTime end, Long courseId) {

        HashMap<Student, Character> result = new HashMap<>();
        Optional<Course> c = courseRepositoryJPA.findById(courseId);
        if (c.isPresent()) {
            List<Grade> grades = gradeRepositoryJPA.findByCourseAndTimestampBetween(c.get(),begin,end);
            for (Grade g : grades) {
                result.put(g.getStudent(), g.getGrade());
            }
        }
        return result;
    }
}

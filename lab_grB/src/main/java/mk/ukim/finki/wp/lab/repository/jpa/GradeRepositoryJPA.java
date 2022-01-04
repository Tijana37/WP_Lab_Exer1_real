package mk.ukim.finki.wp.lab.repository.jpa;

import mk.ukim.finki.wp.lab.model.Course;
import mk.ukim.finki.wp.lab.model.Grade;
import mk.ukim.finki.wp.lab.model.Student;
import org.apache.tomcat.jni.Local;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import org.springframework.data.domain.Pageable;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface GradeRepositoryJPA extends PagingAndSortingRepository<Grade, Long> {
    Page<Grade> findAll(Pageable pageable);
    //Page<Grade> findByIdNotNull(Pageable pageable);
    List<Grade> findByCourse(Course course);
    List<Grade> findByCourseAndStudent(Course course, Student student);
    List<Grade> findByTimestampBetween(LocalDateTime begin, LocalDateTime end);
    List<Grade> findByCourseAndTimestampBetween(Course course, LocalDateTime begin, LocalDateTime end);
}

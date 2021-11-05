package mk.ukim.finki.wp.lab.bootstrap;

import mk.ukim.finki.wp.lab.model.Course;
import mk.ukim.finki.wp.lab.model.Student;
import mk.ukim.finki.wp.lab.repository.StudentRepository;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

@Component
public class DataHolder {
    public static List<Student> students = new ArrayList<>();
    public static List<Course> courses = new ArrayList<>();
    public static int activeSessions = 0;


    @PostConstruct
    public void init(){
        IntStream.range(0,5).forEach(i->students.add(
                new Student("Tijana"+String.valueOf(i),"Tijana"+String.valueOf(i), "Tijana".concat(String.valueOf(i)),"At")));
        courses.add(new Course(1111L, "AI","Artifical Intelligence",new ArrayList<>( new StudentRepository().findAllStudents().subList(0,1))));
        courses.add(new Course(2222L, "ML","Machine Learning",new ArrayList<>( new StudentRepository().findAllStudents().subList(1,2))));
        courses.add(new Course(3333L, "OS","Operating Systems", new ArrayList<>(new StudentRepository().findAllStudents().subList(2,3))));
        courses.add(new Course(4444L, "LA","Linear Algebra", new ArrayList<>(new StudentRepository().findAllStudents().subList(3,4))));
        courses.add(new Course(5555L, "WP","Web Programming", new ArrayList<>(new StudentRepository().findAllStudents().subList(4,5))));


    }

}

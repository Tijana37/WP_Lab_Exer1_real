package mk.ukim.finki.wp.lab.bootstrap;

import mk.ukim.finki.wp.lab.model.Course;
import mk.ukim.finki.wp.lab.model.Student;
import mk.ukim.finki.wp.lab.model.Teacher;
import mk.ukim.finki.wp.lab.repository.StudentRepository;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Component
public class DataHolder {
    public static List<Student> students = new ArrayList<>();
    public static List<Course> courses = new ArrayList<>();
    public static List<Teacher> teachers = new ArrayList<>();
    public static int activeSessions = 0;
    public static Comparator<Course> comparator;

    @PostConstruct
    public void init(){
        //Automatization of the process creating students
        IntStream.range(0,5).forEach(i->students.add(
                new Student("Tijana"+String.valueOf(i),"Tijana"+String.valueOf(i), "Tijana".concat(String.valueOf(i)),"At")));

        //Process creating courses
        courses.add(new Course( "AI","Artifical Intelligence",new ArrayList<>( new StudentRepository().findAllStudents().subList(0,1))));
        courses.add(new Course( "ML","Machine Learning",new ArrayList<>( new StudentRepository().findAllStudents().subList(1,2))));
        courses.add(new Course( "OS","Operating Systems", new ArrayList<>(new StudentRepository().findAllStudents().subList(2,3))));
        courses.add(new Course( "LA","Linear Algebra", new ArrayList<>(new StudentRepository().findAllStudents().subList(3,4))));
        courses.add(new Course( "WP","Web Programming", new ArrayList<>(new StudentRepository().findAllStudents().subList(4,5))));

        //Comparator for sorting courses by name
        comparator = Comparator.comparing(Course::getName);
        courses.sort(comparator);
    }

}

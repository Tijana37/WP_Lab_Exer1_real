package mk.ukim.finki.wp.lab.model;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "teachers")
public class Teacher {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    //private String name;
    //private String surname;
    @Convert(converter = TeacherFullnameConverter.class)
    TeacherFullname teacherFullname;
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private LocalDate dateOfEmployment;

    public Teacher() {
    }

//    public Teacher(String name, String surname) {
//        //this.id = (long) (Math.random() * 1000);
//        this.teacherFullname.setName(name);
//        this.teacherFullname.setSurname(surname);
//    }


    public Teacher(TeacherFullname teacherFullname, LocalDate dateOfEmployment) {
        this.teacherFullname = teacherFullname;
        this.dateOfEmployment = dateOfEmployment;
    }


    public String getName() {
        System.out.println("teacher name: " + teacherFullname.getName());
        return teacherFullname.getName();
    }

    public String getSurname() {
        System.out.println("teacher surname: "+teacherFullname.getSurname());
        return teacherFullname.getSurname();
    }

}

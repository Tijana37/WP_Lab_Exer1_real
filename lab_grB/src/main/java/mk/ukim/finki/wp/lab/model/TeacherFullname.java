package mk.ukim.finki.wp.lab.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class TeacherFullname implements Serializable {
    private String name;
    private String surname;

}

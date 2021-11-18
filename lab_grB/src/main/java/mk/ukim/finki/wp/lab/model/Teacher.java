package mk.ukim.finki.wp.lab.model;

import lombok.Data;

@Data
public class Teacher {
     Long id;
   String name;
     String surname;

    public Teacher(String name, String surname) {
        this.id = (long) (Math.random()*1000);
        this.name = name;
        this.surname = surname;
    }
}

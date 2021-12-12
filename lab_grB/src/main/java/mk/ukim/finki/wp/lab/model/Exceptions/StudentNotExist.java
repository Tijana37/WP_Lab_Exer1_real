package mk.ukim.finki.wp.lab.model.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code= HttpStatus.NOT_FOUND)
public class StudentNotExist extends Exception{
    public StudentNotExist(String username) {
        super(String.format("Student with username: %s is not found", username));
    }
}

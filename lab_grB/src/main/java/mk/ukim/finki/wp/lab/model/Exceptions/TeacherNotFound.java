package mk.ukim.finki.wp.lab.model.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code= HttpStatus.NOT_FOUND)
public class TeacherNotFound extends Exception{
    public TeacherNotFound(String id) {
        super(String.format("Teacher with id: %s is not found", id));
    }
}

package mk.ukim.finki.wp.lab.model.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code= HttpStatus.NOT_FOUND)
public class CourseIDException extends Exception{
    public CourseIDException(Long id) {
        super(String.format("Course with id: %d is not found", id));
        }
}

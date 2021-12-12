package mk.ukim.finki.wp.lab.model.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code= HttpStatus.BAD_REQUEST)
public class coursesSameName extends Exception{
    public class CourseIDException extends Exception{
        public CourseIDException(String name) {
            super(String.format("Course with name: %s already exists", name));
        }
    }

}

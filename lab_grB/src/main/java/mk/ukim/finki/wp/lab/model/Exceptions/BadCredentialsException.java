package mk.ukim.finki.wp.lab.model.Exceptions;

public class BadCredentialsException extends Exception{
    public BadCredentialsException(String message) {
        System.out.println(message);
    }
}

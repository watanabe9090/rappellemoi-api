package dev.ctc.learning.rappellemoiapi.auth.exceptions;

public class UserAlreadyExistsException extends Exception {
    public UserAlreadyExistsException(String pattern, String... vars) {
        super(String.format(pattern, vars));
    }

    public UserAlreadyExistsException(String message) {
        super(message);
    }
}

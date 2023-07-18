package dev.ctc.learning.rappellemoiapi.auth.exceptions;

public class UserDoesNotExistsException extends Exception {
    public UserDoesNotExistsException(String pattern, String... vars) {
        super(String.format(pattern, vars));
    }

    public UserDoesNotExistsException(String message) {
        super(message);
    }
}

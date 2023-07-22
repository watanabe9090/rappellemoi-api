package dev.ctc.learning.rappellemoiapi.deck;

import dev.ctc.learning.rappellemoiapi.auth.exceptions.UserAlreadyExistsException;
import dev.ctc.learning.rappellemoiapi.base.BaseErrorHandler;
import dev.ctc.learning.rappellemoiapi.deck.deck.DeckAlreadyExistsException;
import dev.ctc.learning.rappellemoiapi.deck.deck.DeckDoesNotExistsException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Map;

@ControllerAdvice("dev.ctc.learning.rappellemoiapi")
public class DeckErrorHandler extends BaseErrorHandler {

    @ExceptionHandler(DeckDoesNotExistsException.class)
    public ResponseEntity<Map<String, String>> handleDeckDoesNotExistsException(DeckDoesNotExistsException ex) {
        return new ResponseEntity<>(convert(ex), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(DeckAlreadyExistsException.class)
    public ResponseEntity<Map<String, String>> handleDeckAlreadyExistsException(DeckAlreadyExistsException ex) {
        return new ResponseEntity<>(convert(ex), HttpStatus.CONFLICT);
    }
}

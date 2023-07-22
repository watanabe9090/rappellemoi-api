package dev.ctc.learning.rappellemoiapi.deck.exceptions;

public class DeckDoesNotExistsException extends Exception {
    public DeckDoesNotExistsException(String pattern, String... texts) {
        super(String.format(pattern, texts));
    }
}

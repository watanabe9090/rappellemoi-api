package dev.ctc.learning.rappellemoiapi.deck.exceptions;

public class DeckAlreadyExistsException extends Exception {
    public DeckAlreadyExistsException(String pattern, String... texts) {
        super(String.format(pattern, texts));
    }
}

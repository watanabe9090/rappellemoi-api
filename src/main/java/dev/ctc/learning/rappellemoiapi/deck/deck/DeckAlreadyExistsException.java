package dev.ctc.learning.rappellemoiapi.deck.deck;

public class DeckAlreadyExistsException extends Exception {
    public DeckAlreadyExistsException(String pattern, String... texts) {
        super(String.format(pattern, texts));
    }
}

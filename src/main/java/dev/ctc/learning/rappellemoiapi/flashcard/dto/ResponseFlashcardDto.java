package dev.ctc.learning.rappellemoiapi.flashcard.dto;

import java.util.Date;

public record ResponseFlashcardDto(
        Long id,
        String front,
        String back,
        String deck,
        Date nextRevision
) {
}

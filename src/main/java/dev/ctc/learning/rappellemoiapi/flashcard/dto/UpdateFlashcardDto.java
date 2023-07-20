package dev.ctc.learning.rappellemoiapi.flashcard.dto;

import lombok.Data;

public record UpdateFlashcardDto (
        Long id,
        String front,
        String back
){}

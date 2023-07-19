package dev.ctc.learning.rappellemoiapi.flashcard.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
public record SaveFlashcardDto(
    String front,
    String back
) { }

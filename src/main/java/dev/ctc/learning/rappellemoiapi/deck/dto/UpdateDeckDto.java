package dev.ctc.learning.rappellemoiapi.deck.dto;

public record UpdateDeckDto(
        String oldName,
        String newName
) {
}

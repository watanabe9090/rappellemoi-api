package dev.ctc.learning.rappellemoiapi.deck.dto;

import jakarta.validation.constraints.NotEmpty;

public record SaveDeckDto(
        @NotEmpty(message = "deck 'oldName' cannot be null") String name
) {
}

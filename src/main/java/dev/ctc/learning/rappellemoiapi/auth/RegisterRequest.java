package dev.ctc.learning.rappellemoiapi.auth;

import jakarta.validation.constraints.NotEmpty;

public record RegisterRequest(
        @NotEmpty String email,
        @NotEmpty String password,
        @NotEmpty String username
) {}

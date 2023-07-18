package dev.ctc.learning.rappellemoiapi.auth;

import jakarta.validation.constraints.NotEmpty;

public record AuthenticationRequest(
        @NotEmpty String email,
        @NotEmpty String password
){}

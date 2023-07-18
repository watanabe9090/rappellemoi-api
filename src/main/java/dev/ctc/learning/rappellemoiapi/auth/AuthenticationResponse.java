package dev.ctc.learning.rappellemoiapi.auth;

import lombok.Builder;

import java.util.Date;

@Builder
public record AuthenticationResponse (
        String token,
        Long expiresAt
) {}

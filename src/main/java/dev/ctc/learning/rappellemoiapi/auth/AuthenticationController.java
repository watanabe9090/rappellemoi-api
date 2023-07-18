package dev.ctc.learning.rappellemoiapi.auth;

import dev.ctc.learning.rappellemoiapi.auth.exceptions.UserAlreadyExistsException;
import dev.ctc.learning.rappellemoiapi.auth.exceptions.UserDoesNotExistsException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest body) throws UserDoesNotExistsException {
        return ResponseEntity.ok(authenticationService.authenticate(body));
    }

    @PostMapping("registration")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody @Valid RegisterRequest body) throws UserAlreadyExistsException {
        return ResponseEntity.ok(authenticationService.register(body));
    }


}

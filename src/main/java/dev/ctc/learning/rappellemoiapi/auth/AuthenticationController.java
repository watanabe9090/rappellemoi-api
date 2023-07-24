package dev.ctc.learning.rappellemoiapi.auth;

import dev.ctc.learning.rappellemoiapi.auth.exceptions.UserAlreadyExistsException;
import dev.ctc.learning.rappellemoiapi.auth.exceptions.UserDoesNotExistsException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

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

    @PreAuthorize("hasAuthority('USER')")
    @GetMapping("/check")
    public ResponseEntity<Void> check() {
        return new ResponseEntity(HttpStatus.OK);
    }



}

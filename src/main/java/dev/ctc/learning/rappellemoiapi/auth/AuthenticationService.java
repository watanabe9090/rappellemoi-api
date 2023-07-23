package dev.ctc.learning.rappellemoiapi.auth;

import dev.ctc.learning.rappellemoiapi.auth.exceptions.UserAlreadyExistsException;
import dev.ctc.learning.rappellemoiapi.auth.exceptions.UserDoesNotExistsException;
import dev.ctc.learning.rappellemoiapi.configs.JwtService;
import dev.ctc.learning.rappellemoiapi.token.Token;
import dev.ctc.learning.rappellemoiapi.token.TokenRepository;
import dev.ctc.learning.rappellemoiapi.token.TokenType;
import dev.ctc.learning.rappellemoiapi.user.Role;
import dev.ctc.learning.rappellemoiapi.user.User;
import dev.ctc.learning.rappellemoiapi.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final TokenRepository tokenRepository;

    public AuthenticationResponse register(RegisterRequest body) throws UserAlreadyExistsException {
        if(userRepository.existsByEmail(body.email())) {
            throw new UserAlreadyExistsException("User with email '%s' already exists", body.email());
        }
        if(userRepository.existsByUsername(body.username())) {
            throw new UserAlreadyExistsException("User with username '%s' already exists", body.username());
        }
        final String encodedPassword = passwordEncoder.encode(body.password());
        final User newUser = User.builder()
                .email(body.email())
                .username(body.username())
                .password(encodedPassword)
                .role(Role.USER)
                .enable(false)
                .locked(true)
                .expired(false)
                .build();
        final User savedUser = userRepository.save(newUser);
        final String jwtToken = jwtService.generateToken(newUser);
        saveJwtToken(savedUser, jwtToken);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .expiresAt(jwtService.extractExpiration(jwtToken).getTime())
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest body) throws UserDoesNotExistsException {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(body.email(), body.password())
        );
        Optional<User> possibleUser = userRepository.findByEmail(body.email());
        if(possibleUser.isEmpty()) {
            throw new UserDoesNotExistsException("User does not exists for email '%s'", body.email());
        }
        final User user = possibleUser.get();
        final String jwtToken = jwtService.generateToken(user);
        revokeAllUserTokens(user);
        saveJwtToken(user, jwtToken);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .expiresAt(jwtService.extractExpiration(jwtToken).getTime())
                .build();
    }

    private void revokeAllUserTokens(User user) {
        List<Token> validTokens = tokenRepository.findAllValidTokensByUser(user.getId());
        if(validTokens.isEmpty()) {
            return;
        }
        List<Token> invalidTokens = validTokens.stream().map(t -> {
            t.setRevoked(true);
            t.setExpired(true);
            return t;
        })
        .toList();
        tokenRepository.saveAll(invalidTokens);
    }

    private void saveJwtToken(User user, String jwtToken) {
        tokenRepository.save(Token.builder()
                .user(user)
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build());
    }
}

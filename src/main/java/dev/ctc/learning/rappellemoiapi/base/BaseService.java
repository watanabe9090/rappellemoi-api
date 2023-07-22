package dev.ctc.learning.rappellemoiapi.base;

import dev.ctc.learning.rappellemoiapi.user.User;
import dev.ctc.learning.rappellemoiapi.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BaseService {
    private final UserRepository userRepository;

    protected User getUser() {
        String userEmail = (String) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();
        return userRepository.findByEmail(userEmail)
                .orElseThrow();
    }
}

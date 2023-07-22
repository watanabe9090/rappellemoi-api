package dev.ctc.learning.rappellemoiapi.token;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class TokenGarbageCollector {
    private final TokenRepository tokenRepository;

    @Scheduled(cron = "0 0 0 * * *")
    @Transactional
    public synchronized void cleanInvalidTokens() {
        log.info("Cleaning expired tokens");
        tokenRepository.deleteByExpired(true);
    }
}

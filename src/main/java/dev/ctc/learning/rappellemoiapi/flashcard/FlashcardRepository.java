package dev.ctc.learning.rappellemoiapi.flashcard;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface FlashcardRepository extends JpaRepository<Flashcard, Long> {
    List<Flashcard> findByUserId(UUID userId);
    Optional<Flashcard> findByIdAndUserId(Long id, UUID userId);
//    void deleteByIdAndUserId(Long id, UUID userId);
    List<Flashcard> findByFrontContainingIgnoreCaseOrBackContainingIgnoreCase(String front, String back);
}

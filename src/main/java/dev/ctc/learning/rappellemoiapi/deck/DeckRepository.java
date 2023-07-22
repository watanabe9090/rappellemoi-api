package dev.ctc.learning.rappellemoiapi.deck;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface DeckRepository extends JpaRepository<Deck, Long> {
    List<Deck> findByIdAndUserId(Long id, UUID userId);
    Optional<Deck> findByNameAndUserId(String name, UUID userId);
    List<Deck> findByUserId(UUID userId);
    List<Deck> findByNameContainingIgnoreCase(String name);

}

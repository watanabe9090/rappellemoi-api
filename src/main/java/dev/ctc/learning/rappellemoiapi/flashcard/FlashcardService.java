package dev.ctc.learning.rappellemoiapi.flashcard;

import dev.ctc.learning.rappellemoiapi.flashcard.dto.SaveFlashcardDto;
import dev.ctc.learning.rappellemoiapi.flashcard.dto.UpdateFlashcardDto;
import dev.ctc.learning.rappellemoiapi.user.User;
import dev.ctc.learning.rappellemoiapi.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FlashcardService {

    private final FlashcardRepository flashcardRepository;
    private final UserRepository userRepository;


    private User getUser() {
        String userEmail = (String) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();
        return userRepository.findByEmail(userEmail)
                .orElseThrow();
    }



    public List<Flashcard> getMyFlashcards(String query) {
        List<Flashcard> myFlashcards = flashcardRepository.findByFrontContainingIgnoreCaseOrBackContainingIgnoreCase(query, query);
        return myFlashcards;
    }

    public List<Flashcard> getAllMyFlashcards() {
        User user = getUser();
        List<Flashcard> myCards = flashcardRepository.findByUserId(user.getId());
        return myCards;
    }

    public void save(SaveFlashcardDto dto) {
        Flashcard newFlashcard = Flashcard.builder()
                .user(getUser())
                .front(dto.front())
                .back(dto.back())
                .nextRevision(new Date())
                .gradient(1)
                .build();
        flashcardRepository.save(newFlashcard);
    }

    public void update(UpdateFlashcardDto dto) {
        User user = getUser();
        flashcardRepository.findByIdAndUserId(dto.id(),user.getId());
    }
}

package dev.ctc.learning.rappellemoiapi.flashcard;

import dev.ctc.learning.rappellemoiapi.flashcard.dto.ResponseFlashcardDto;
import dev.ctc.learning.rappellemoiapi.flashcard.dto.SaveFlashcardDto;
import dev.ctc.learning.rappellemoiapi.flashcard.dto.UpdateFlashcardDto;
import dev.ctc.learning.rappellemoiapi.user.User;
import dev.ctc.learning.rappellemoiapi.user.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.*;

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

    public List<Flashcard> getFlashcards(String query) {
        List<Flashcard> myFlashcards = flashcardRepository.findByFrontContainingIgnoreCaseOrBackContainingIgnoreCase(query, query);
        return myFlashcards;
    }

    public List<Flashcard> getAllFlashcards() {
        User user = getUser();
        List<Flashcard> myCards = flashcardRepository.findByUserId(user.getId());
        return myCards;
    }

    @Transactional
    public void save(List<SaveFlashcardDto> dtos) {
        List<Flashcard> flashcards = dtos.stream().map(dto -> Flashcard.builder()
                        .user(getUser())
                        .front(dto.front())
                        .back(dto.back())
                        .nextRevision(new Date())
                        .gradient(1)
                        .build())
                .toList();
        if(!flashcards.isEmpty()) {
            flashcardRepository.saveAll(flashcards);
        }
    }

    public void update(List<UpdateFlashcardDto> dtos) {
        User user = getUser();
        List<Flashcard> updatedFlashcards = dtos.stream()
                .map(dto -> Pair.of(dto, flashcardRepository.findByIdAndUserId(dto.id(), user.getId())))
                .filter(entry -> entry.getValue().isPresent())
                .map(entry -> Pair.of(entry.getKey(), entry.getValue().get()))
                .map(entry -> {
                    entry.getValue().setFront(entry.getKey().front());
                    entry.getValue().setBack(entry.getKey().back());
                    return entry.getValue();
                })
                .toList();
        flashcardRepository.saveAll(updatedFlashcards);
    }

    public void delete(Long id) {
        User user = getUser();
        Flashcard flashcard = flashcardRepository.findByIdAndUserId(id, user.getId())
                .orElseThrow();
        flashcardRepository.delete(flashcard);
    }

    public List<ResponseFlashcardDto> convert(List<Flashcard> flashcard) {
        return flashcard.stream()
                .map(f -> new ResponseFlashcardDto(
                        f.getId(),
                        f.getFront(),
                        f.getBack(),
                        f.getNextRevision()))
                .toList();
    }
}

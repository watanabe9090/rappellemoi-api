package dev.ctc.learning.rappellemoiapi.flashcard;

import dev.ctc.learning.rappellemoiapi.base.BaseService;
import dev.ctc.learning.rappellemoiapi.deck.Deck;
import dev.ctc.learning.rappellemoiapi.deck.DeckRepository;
import dev.ctc.learning.rappellemoiapi.deck.exceptions.DeckDoesNotExistsException;
import dev.ctc.learning.rappellemoiapi.flashcard.dto.ApplyRevisionDto;
import dev.ctc.learning.rappellemoiapi.flashcard.dto.ResponseFlashcardDto;
import dev.ctc.learning.rappellemoiapi.flashcard.dto.SaveFlashcardDto;
import dev.ctc.learning.rappellemoiapi.flashcard.dto.UpdateFlashcardDto;
import dev.ctc.learning.rappellemoiapi.user.User;
import dev.ctc.learning.rappellemoiapi.user.UserRepository;
import jakarta.transaction.Transactional;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;

@Service
public class FlashcardService extends BaseService {

    private final FlashcardRepository flashcardRepository;
    private final RevisionGradientService revisionGradientService;
    private final DeckRepository deckRepository;

    public FlashcardService(UserRepository userRepository, FlashcardRepository flashcardRepository, RevisionGradientService revisionGradientService, DeckRepository deckRepository) {
        super(userRepository);
        this.flashcardRepository = flashcardRepository;
        this.revisionGradientService = revisionGradientService;
        this.deckRepository = deckRepository;
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
    public void save(List<SaveFlashcardDto> dtos) throws DeckDoesNotExistsException {
        User user = getUser();
        Map<Boolean, List<Pair<SaveFlashcardDto, Optional<Deck>>>> possibleDecks = dtos.stream()
                .map(dto -> Pair.of(dto, deckRepository.findByNameAndUserId(dto.deckName(), user.getId())))
                .collect(groupingBy(pair -> pair.getValue().isPresent()));
        if(possibleDecks.get(false) != null && !possibleDecks.get(false).isEmpty()) {
            String deckNamesPattern = possibleDecks.get(false).stream()
                    .map(pair -> pair.getKey().deckName())
                    .distinct()
                    .collect(Collectors.joining(", "));
            throw new DeckDoesNotExistsException("reference(s) to deck(s) %s does not exists", deckNamesPattern);
        }
        List<Flashcard> newFlashcards = possibleDecks.get(true).stream()
                .map(dto -> Flashcard.builder()
                        .user(user)
                        .front(dto.getKey().front())
                        .back(dto.getKey().back())
                        .deck(dto.getValue().get())
                        .nextRevision(new Date())
                        .gradient(1)
                        .build()
                )
                .toList();
        if(!newFlashcards.isEmpty()) {
            flashcardRepository.saveAll(newFlashcards);
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
                        f.getDeck().getName(),
                        f.getNextRevision()))
                .toList();
    }

    public void applyRevision(List<ApplyRevisionDto> dtos) {
        User user = getUser();
        List<Flashcard> revisedFlashcards = dtos.stream()
                .map(dto -> Pair.of(dto, flashcardRepository.findByIdAndUserId(dto.id(), user.getId())))
                .map(entry -> Pair.of(entry.getKey(), entry.getValue().get()))
                .map(entry -> {
                    String degree = entry.getKey().degree();
                    Integer gradient = entry.getValue().getGradient();
                    Date currentRevision = entry.getValue().getNextRevision();

//                    Date nextRevision = revisionGradientService.calculateNextRevision(currentRevision, gradient, degree);
//                    entry.getValue().setNextRevision(nextRevision);
                    return entry.getValue();
                })
                .toList();
        flashcardRepository.saveAll(revisedFlashcards);
    }
}

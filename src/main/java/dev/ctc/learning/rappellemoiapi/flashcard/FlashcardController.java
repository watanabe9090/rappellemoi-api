package dev.ctc.learning.rappellemoiapi.flashcard;


import dev.ctc.learning.rappellemoiapi.flashcard.dto.SaveFlashcardDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("flashcards")
@PreAuthorize("hasAuthority('USER')")
public class FlashcardController {

    private final FlashcardService flashcardService;

    @GetMapping()
    public ResponseEntity<List<Flashcard>> helloWorld() {
        List<Flashcard> myFlashcards = flashcardService.getMyFlashcards();
        return new ResponseEntity<>(myFlashcards, HttpStatus.OK);
    }


    @PostMapping
    public ResponseEntity<String> postMyFlashcard(@RequestBody @Valid SaveFlashcardDto dto) {
        flashcardService.save(dto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }




}

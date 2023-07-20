package dev.ctc.learning.rappellemoiapi.flashcard;


import dev.ctc.learning.rappellemoiapi.flashcard.dto.ResponseFlashcardDto;
import dev.ctc.learning.rappellemoiapi.flashcard.dto.SaveFlashcardDto;
import dev.ctc.learning.rappellemoiapi.flashcard.dto.UpdateFlashcardDto;
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

    @GetMapping
    public ResponseEntity<List<ResponseFlashcardDto>> getMyCards(
            @RequestParam(name = "q", defaultValue = "", required = false) String query
    ) {
        List<ResponseFlashcardDto> myFlashcards;
        if(!query.isEmpty()) {
            myFlashcards = flashcardService.convert(flashcardService.getFlashcards(query));
        } else {
            myFlashcards = flashcardService.convert(flashcardService.getAllFlashcards());
        }
        return new ResponseEntity<>(myFlashcards, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Void> postMyFlashcard(@RequestBody @Valid SaveFlashcardDto dto) {
        flashcardService.save(dto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<Void> putMyFlashcard(@RequestBody @Valid UpdateFlashcardDto dto) {
        flashcardService.update(dto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMyFlashcard(@PathVariable Long id) {
        flashcardService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


}

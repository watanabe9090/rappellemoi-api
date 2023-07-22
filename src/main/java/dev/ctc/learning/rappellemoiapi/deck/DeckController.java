package dev.ctc.learning.rappellemoiapi.deck;


import dev.ctc.learning.rappellemoiapi.deck.deck.DeckAlreadyExistsException;
import dev.ctc.learning.rappellemoiapi.deck.deck.DeckDoesNotExistsException;
import dev.ctc.learning.rappellemoiapi.deck.dto.ResponseDeckDto;
import dev.ctc.learning.rappellemoiapi.deck.dto.SaveDeckDto;
import dev.ctc.learning.rappellemoiapi.deck.dto.UpdateDeckDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("decks")
@PreAuthorize("hasAuthority('USER')")
public class DeckController {

    private final DeckService deckService;

    @GetMapping
    public ResponseEntity<List<ResponseDeckDto>> getMyDecks(
            @RequestParam(name = "q", defaultValue = "", required = false) String query
    ) {
        List<ResponseDeckDto> myDecks;
        if(!query.isEmpty()) {
            myDecks = deckService.convert(deckService.getDecks(query));
        } else {
            myDecks = deckService.convert(deckService.getAllDecks());
        }
        return new ResponseEntity<>(myDecks, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Void> saveMyDeck(
            @RequestBody @Valid SaveDeckDto dto
    ) throws DeckAlreadyExistsException {
        deckService.save(dto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<Void> updateMyDeck(
            @RequestBody @Valid UpdateDeckDto dto
    ) throws DeckDoesNotExistsException {
        deckService.update(dto);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/{oldName}")
    public ResponseEntity<Void> updateMyDeck(
            @PathVariable(name = "oldName") String deckName
    ) throws DeckDoesNotExistsException {
        deckService.delete(deckName);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
package dev.ctc.learning.rappellemoiapi.deck;

import dev.ctc.learning.rappellemoiapi.base.BaseService;
import dev.ctc.learning.rappellemoiapi.deck.exceptions.DeckAlreadyExistsException;
import dev.ctc.learning.rappellemoiapi.deck.exceptions.DeckDoesNotExistsException;
import dev.ctc.learning.rappellemoiapi.deck.dto.ResponseDeckDto;
import dev.ctc.learning.rappellemoiapi.deck.dto.SaveDeckDto;
import dev.ctc.learning.rappellemoiapi.deck.dto.UpdateDeckDto;
import dev.ctc.learning.rappellemoiapi.user.User;
import dev.ctc.learning.rappellemoiapi.user.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeckService extends BaseService {
    private final DeckRepository deckRepository;

    public DeckService(UserRepository userRepository, DeckRepository deckRepository) {
        super(userRepository);
        this.deckRepository = deckRepository;
    }


    public List<Deck> getAllDecks() {
        User user = getUser();
        return deckRepository.findByUserId(user.getId());
    }

    public List<Deck> getDecks(String query) {
        List<Deck> decks = deckRepository.findByNameContainingIgnoreCase(query);
        return decks;
    }

    public void save(SaveDeckDto dto) throws DeckAlreadyExistsException {
        User user = getUser();
        if(deckRepository.findByNameAndUserId(dto.name(), user.getId()).isPresent()) {
            throw new DeckAlreadyExistsException("deck with oldName %s already exists", dto.name());
        }
        Deck newDeck = Deck.builder()
                .name(dto.name())
                .user(user)
                .build();
        deckRepository.save(newDeck);
    }

    public void update(UpdateDeckDto dto) throws DeckDoesNotExistsException {
        User user = getUser();
        Deck oldDeck = deckRepository.findByNameAndUserId(dto.oldName(), user.getId())
                .orElseThrow(() -> new DeckDoesNotExistsException("deck with oldName %s does not exists", dto.oldName()));
        oldDeck.setName(dto.newName());
        deckRepository.save(oldDeck);
    }

    public void delete(String deckName) throws DeckDoesNotExistsException {
        User user = getUser();
        Deck oldDeck = deckRepository.findByNameAndUserId(deckName, user.getId())
                .orElseThrow(() -> new DeckDoesNotExistsException("deck with oldName %s does not exists", deckName));
        deckRepository.delete(oldDeck);
    }

    public List<ResponseDeckDto> convert(List<Deck> decks) {
        return decks.stream()
                .map(d -> new ResponseDeckDto(
                        d.getId(),
                        d.getName()))
                .toList();
    }
}

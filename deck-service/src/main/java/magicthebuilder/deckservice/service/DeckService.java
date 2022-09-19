package magicthebuilder.deckservice.service;


import magicthebuilder.deckservice.dto.CreateDeckDto;
import magicthebuilder.deckservice.dto.DetailedDeckGetResponseDto;
import magicthebuilder.deckservice.dto.SimpleDeckGetResponseDto;
import magicthebuilder.deckservice.entity.Deck;
import magicthebuilder.deckservice.entity.User;
import magicthebuilder.deckservice.entity.enums.DeckAccessLevelEnum;
import magicthebuilder.deckservice.exception.customexceptions.InaccessibleDeckException;
import magicthebuilder.deckservice.exception.customexceptions.UnrecognizedDeckException;
import magicthebuilder.deckservice.exception.customexceptions.UnrecognizedUserIdException;
import magicthebuilder.deckservice.repository.DeckRepository;
import magicthebuilder.deckservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class DeckService {

    @Autowired
    private DeckRepository repository;

    @Autowired
    private UserRepository userRepository;

    public List<SimpleDeckGetResponseDto> getAllDecks() {
        List<Deck> decks = repository.findAll();
        return decks.stream()
                .map(SimpleDeckGetResponseDto::new)
                .toList();
    }

    public List<SimpleDeckGetResponseDto> getPublicDecks() {
        List<Deck> decks = repository.findAllByAccessLevel(DeckAccessLevelEnum.PUBLIC);
        return decks.stream()
                .map(SimpleDeckGetResponseDto::new)
                .toList();
    }

    public DetailedDeckGetResponseDto getDeckByIdAndUserId(UUID id, Long userId) {
         if(repository.findById(id).isPresent()) {
             Deck deck = repository.findById(id).get();
             if(deck.getAccessLevel() == DeckAccessLevelEnum.PRIVATE) {
                 if (userId.equals(deck.getOwner().getId())) {
                     return new DetailedDeckGetResponseDto(deck);
                 } else {
                     throw new InaccessibleDeckException(id);
                 }
             } else {
                 return new DetailedDeckGetResponseDto(deck);
             }
         } else {
             throw new UnrecognizedDeckException(id);
         }
    }

    public UUID addDeck(Deck deck) {
        repository.save(deck);
        return deck.getUuid();
    }

    public Deck createDeckFromDto(CreateDeckDto dto) {
        Optional<User> owner = userRepository.findById(dto.getUserId());
        if(owner.isPresent()) {
            Deck ret = CreateDeckDto.dtoToEntityMapper().apply(dto);
            ret.setOwner(owner.get());
            return ret;
        }
        else {
            throw new UnrecognizedUserIdException(dto.getUserId());
        }
    }

    public void flushDatabase() {
        repository.deleteAll();
    }
}

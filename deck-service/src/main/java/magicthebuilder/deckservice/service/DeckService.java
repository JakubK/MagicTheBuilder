package magicthebuilder.deckservice.service;


import magicthebuilder.deckservice.dto.*;
import magicthebuilder.deckservice.entity.Card;
import magicthebuilder.deckservice.entity.Deck;
import magicthebuilder.deckservice.entity.User;
import magicthebuilder.deckservice.entity.enums.DeckAccessLevelEnum;
import magicthebuilder.deckservice.exception.customexceptions.InaccessibleDeckException;
import magicthebuilder.deckservice.exception.customexceptions.InaccessibleResourceException;
import magicthebuilder.deckservice.exception.customexceptions.UnrecognizedCardIdException;
import magicthebuilder.deckservice.exception.customexceptions.UnrecognizedDeckException;
import magicthebuilder.deckservice.repository.DeckRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class DeckService {

    private final CardService cardService = new CardService();
    @Autowired
    private DeckRepository repository;
    @Autowired
    private UserService userService;


    public DetailedDeckGetResponseDto getNotPrivateDeck(UUID id) {
        Deck deck = getDeckById(id);
        if (deck.getAccessLevel() != DeckAccessLevelEnum.PRIVATE) {
            return new DetailedDeckGetResponseDto(deck);
        } else {
            throw new InaccessibleDeckException(id);
        }
    }

    public DetailedDeckGetResponseDto getOwnersDeck(UUID id, Long userId) {
        Deck deck = getDeckById(id);
        if (deck.getOwner().getId().equals(userId) || deck.getAccessLevel() != DeckAccessLevelEnum.PRIVATE) {
            return new DetailedDeckGetResponseDto(deck);
        } else {
            throw new InaccessibleDeckException(id);
        }
    }


    public List<SimpleDeckGetResponseDto> getPublicDecks(Pageable paging) {

        Page<Deck> decks = repository.findAllByAccessLevel(DeckAccessLevelEnum.PUBLIC, paging);

        return decks.stream()
                .map(SimpleDeckGetResponseDto::new)
                .toList();
    }

    public List<SimpleDeckGetResponseDto> getUserPublicDecks(Long userId) {
        return repository.findAllByAccessLevelAndOwner(DeckAccessLevelEnum.PUBLIC, userService.findById(userId)).stream()
                .map(SimpleDeckGetResponseDto::new)
                .toList();
    }

    public List<SimpleDeckGetResponseDto> getUserDecks(Long userId) {
        return repository.findAllByOwner(userService.findById(userId)).stream()
                .map(SimpleDeckGetResponseDto::new)
                .toList();
    }

    public DetailedDeckGetResponseDto getDeckByIdAndUserId(UUID id, Long userId) {
        Deck deck = getDeckById(id);
        if (deck.getAccessLevel() == DeckAccessLevelEnum.PRIVATE) {
            if (userId.equals(deck.getOwner().getId())) {
                return new DetailedDeckGetResponseDto(deck);
            } else {
                throw new InaccessibleDeckException(id);
            }
        } else {
            return new DetailedDeckGetResponseDto(deck);
        }
    }

    public List<SimpleDeckGetResponseDto> getDecksByUserId(Long userId) {
        List<Deck> decks = repository.findAllByOwner(userService.findById(userId));
        return decks.stream()
                .map(SimpleDeckGetResponseDto::new)
                .toList();
    }


    public void addDeck(Deck deck) {
        repository.save(deck);
    }

    public UUID createDeckFromDto(CreateDeckDto dto, long userId) {
        User owner = userService.findById(userId);
        Deck ret = CreateDeckDto.dtoToEntityMapper().apply(dto);
        ret.setOwner(owner);
        //validateDeck(ret);
        addDeck(ret);
        return ret.getUuid();

    }

    public DetailedDeckGetResponseDto updateDeck(DeckUpdateRequestDto deckDto, long userId) {
        Deck deck = getDeckById(deckDto.getId());
        if (deck.getOwner().getId() != userId) {
            throw new InaccessibleResourceException(userId);
        }
        deck.setAccessLevel(deckDto.getAccessLevel());
        deck.setGameMode(deckDto.getGameMode());
        deck.setName(deckDto.getName());
        deck.setCards(getCardsFromCardMultipleCardDtoList(deckDto.getDeckCards()));
        deck.setSideboard(getCardsFromCardMultipleCardDtoList(deckDto.getSideboardCards()));
        // validateDeck(deck);
        repository.save(deck);
        return getDeckByIdAndUserId(deckDto.getId(), deck.getOwner().getId());
    }

    public void deleteDeck(UUID deckId, long userId) {
        Deck deck = getDeckById(deckId);
        if (deck.getOwner().getId() == userId) {
            repository.deleteById(deckId);
        } else {
            throw new InaccessibleResourceException(userId);
        }
    }

    public void flushDatabase() {
        repository.deleteAll();
    }

    private List<Card> getCardsFromCardMultipleCardDtoList(List<MultipleCardDto> cards) {
        Map<String, Integer> map = new HashMap<>();
        for (MultipleCardDto card : cards) {
            map.put(card.getCardId(), card.getAmount());
        }
        List<Card> ret = new ArrayList<>();
        for (String cardId : map.keySet()) {
            if (!cardService.checkIfCardExists(cardId)) {
                throw new UnrecognizedCardIdException("Unrecognized card with id: " + cardId);
            }
            for (int j = 0; j < map.get(cardId); j++) {
                ret.add(cardService.getCardById(cardId));
            }
        }
        return ret;
    }

    private Deck getDeckById(UUID id) {
        Optional<Deck> optDeck = repository.findById(id);
        if (optDeck.isPresent()) {
            return optDeck.get();
        } else {
            throw new UnrecognizedDeckException(id);
        }

    }

    private void validateDeck(Deck deck) {
        //extend this to check if deck is legal in selected gameMode in the future


    }
}

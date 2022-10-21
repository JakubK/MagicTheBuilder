package magicthebuilder.deckservice.service;


import magicthebuilder.deckservice.dto.*;
import magicthebuilder.deckservice.entity.Card;
import magicthebuilder.deckservice.entity.Deck;
import magicthebuilder.deckservice.entity.User;
import magicthebuilder.deckservice.entity.enums.DeckAccessLevelEnum;
import magicthebuilder.deckservice.entity.enums.GameMode;
import magicthebuilder.deckservice.exception.customexceptions.*;
import magicthebuilder.deckservice.repository.DeckRepository;
import magicthebuilder.deckservice.repository.UserRepository;
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
    private UserRepository userRepository;

    public List<SimpleDeckGetResponseDto> getAllDecks() {
        List<Deck> decks = repository.findAll();
        return decks.stream()
                .map(SimpleDeckGetResponseDto::new)
                .toList();
    }

    public List<SimpleDeckGetResponseDto> getPublicDecks(Pageable paging) {

        Page<Deck> pageTuts;
        Page<Deck> decks = repository.findAllByAccessLevel(DeckAccessLevelEnum.PUBLIC, paging);

        return decks.stream()
                .map(SimpleDeckGetResponseDto::new)
                .toList();


    }

    public DetailedDeckGetResponseDto getDeckByIdAndUserId(UUID id, Long userId) {
        if (repository.findById(id).isPresent()) {
            Deck deck = repository.findById(id).get();
            if (deck.getAccessLevel() == DeckAccessLevelEnum.PRIVATE) {
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

    public List<SimpleDeckGetResponseDto> getDecksByUserId(Long userId) {
        List<Deck> decks = repository.findAllByOwner(userId);
        return decks.stream()
                .map(SimpleDeckGetResponseDto::new)
                .toList();
    }

    public UUID addDeck(Deck deck) {
        repository.save(deck);
        return deck.getUuid();
    }

    public Deck createDeckFromDto(CreateDeckDto dto) {
        Optional<User> owner = userRepository.findById(dto.getUserId());
        if (owner.isPresent()) {
            Deck ret = CreateDeckDto.dtoToEntityMapper().apply(dto);
            ret.setOwner(owner.get());
            validateDeck(ret);
            return ret;

        } else {
            throw new UnrecognizedUserIdException(dto.getUserId());
        }
    }

    public DetailedDeckGetResponseDto updateDeck(DeckUpdateRequestDto deckDto) {
        Optional<Deck> optDeck = repository.findById(deckDto.getId());
        if (optDeck.isEmpty()) {
            throw new UnrecognizedDeckException(deckDto.getId());
        }
        Deck deck = optDeck.get();
        deck.setAccessLevel(deckDto.getAccessLevel());
        deck.setGameMode(deckDto.getGameMode());
        deck.setName(deckDto.getName());
        deck.setCards(getCardsFromCardMultipleCardDtoList(deckDto.getDeckCards()));
        deck.setSideboard(getCardsFromCardMultipleCardDtoList(deckDto.getSideboardCards()));
        validateDeck(deck);
        repository.save(deck);
        return getDeckByIdAndUserId(deckDto.getId(), deck.getOwner().getId());
    }

    public void deleteDeck(UUID deckId) {
        repository.deleteById(deckId);
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
            if (!cardService.checkCardExistance(cardId)) {
                throw new UnrecognizedCardIdException("Unrecognized card with id: " + cardId);
            }
            for (int j = 0; j < map.get(cardId); j++) {
                ret.add(cardService.getCard(cardId));
            }
        }
        return ret;
    }

    private void validateDeck(Deck deck) {
        //extend this to check if deck is legal in selected gameMode in the future
        if (deck.getGameMode().equals(GameMode.DRAFT)) {
            return;
        }
        if (deck.getCards().size() < 60) {
            throw new InvalidDeckDataException("Deck must have at least 60 cards. If you still want to save your deck," +
                    "please set the game mode to DRAFT");
        } else if (deck.getSideboard().size() > 15) {
            throw new InvalidDeckDataException("Deck sideboard can have maximum 15 cards. If you still want to save your deck," +
                    "please set the game mode to DRAFT");
        }

    }
}

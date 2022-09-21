package magicthebuilder.deckservice.service;


import magicthebuilder.deckservice.dto.*;
import magicthebuilder.deckservice.entity.Card;
import magicthebuilder.deckservice.entity.Deck;
import magicthebuilder.deckservice.entity.User;
import magicthebuilder.deckservice.entity.enums.DeckAccessLevelEnum;
import magicthebuilder.deckservice.exception.customexceptions.InaccessibleDeckException;
import magicthebuilder.deckservice.exception.customexceptions.UnrecognizedCardIdException;
import magicthebuilder.deckservice.exception.customexceptions.UnrecognizedDeckException;
import magicthebuilder.deckservice.exception.customexceptions.UnrecognizedUserIdException;
import magicthebuilder.deckservice.repository.DeckRepository;
import magicthebuilder.deckservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class DeckService {

    @Autowired
    private DeckRepository repository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CardService cardService;

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

    public UUID addDeck(Deck deck) {
        repository.save(deck);
        return deck.getUuid();
    }

    public Deck createDeckFromDto(CreateDeckDto dto) {
        Optional<User> owner = userRepository.findById(dto.getUserId());
        if (owner.isPresent()) {
            Deck ret = CreateDeckDto.dtoToEntityMapper().apply(dto);
            ret.setOwner(owner.get());
            // PLACE FOR VALIDATING DECK  //
            return ret;

        } else {
            throw new UnrecognizedUserIdException(dto.getUserId());
        }
    }

    public DetailedDeckGetResponseDto updateDeck(DeckUpdateRequestDto deckDto) {

        Map<String, Integer> deckToAdd = new HashMap<>();
        for (MultipleCardDto card : deckDto.getDeckCardsToAdd()) {
            deckToAdd.put(card.getCardId(), card.getAmount());
        }
        Map<String, Integer> deckToRemove = new HashMap<>();
        for (MultipleCardDto card : deckDto.getDeckCardsToRemove()) {
            deckToRemove.put(card.getCardId(), card.getAmount());
        }
        Map<String, Integer> sideboardToAdd = new HashMap<>();
        for (MultipleCardDto card : deckDto.getSideboardCardsToAdd()) {
            sideboardToAdd.put(card.getCardId(), card.getAmount());
        }
        Map<String, Integer> sideboardToRemove = new HashMap<>();
        for (MultipleCardDto card : deckDto.getSideboardCardsToRemove()) {
            sideboardToRemove.put(card.getCardId(), card.getAmount());
        }

        Optional<Deck> optDeck = repository.findById(deckDto.getId());
        if (optDeck.isEmpty()) {
            throw new UnrecognizedDeckException(deckDto.getId());
        }
        Deck deck = optDeck.get();
        deck.setAccessLevel(deckDto.getAccessLevel());
        deck.setGameMode(deckDto.getGamemode());
        deck.setName(deckDto.getName());
        List<Card> deckCards = deck.getCards();
        List<Card> sideboardCards = deck.getSideboard();
        deckCards.addAll(getCardListFromMap(deckToAdd));
        deckCards.removeAll(getCardListFromMap(deckToRemove));
        sideboardCards.addAll(getCardListFromMap(sideboardToAdd));
        sideboardCards.removeAll(getCardListFromMap(sideboardToRemove));
        deck.setCards(deckCards);
        deck.setSideboard(sideboardCards);
        //ADD DECK VALIDATION HERE//
        repository.save(deck);
        return getDeckByIdAndUserId(deckDto.getId(), deck.getOwner().getId());
    }

    public void flushDatabase() {
        repository.deleteAll();
    }


    private List<Card> getCardListFromMap(Map<String, Integer> inputMap) {
        List<Card> ret = new ArrayList<>();
        for (String cardId : inputMap.keySet()) {
            if (!cardService.checkCardExistance(cardId)) {
                throw new UnrecognizedCardIdException("Unrecognized card with id: " + cardId);
            }
            for (int j = 0; j < inputMap.get(cardId); j++) {
                ret.add(cardService.getCard(cardId));
            }
        }
        return ret;
    }
}

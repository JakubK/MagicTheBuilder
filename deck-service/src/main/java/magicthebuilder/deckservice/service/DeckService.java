package magicthebuilder.deckservice.service;


import magicthebuilder.deckservice.dto.CreateDeckDto;
import magicthebuilder.deckservice.dto.DetailedDeckGetResponseDto;
import magicthebuilder.deckservice.dto.SimpleDeckGetResponseDto;
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
            // PLACE FOR VALIDATING DECK  //
            return ret;

        }
        else {
            throw new UnrecognizedUserIdException(dto.getUserId());
        }
    }

    public DetailedDeckGetResponseDto updateDeck(UUID id, Map<String,Integer> deckToAddMap, Map<String,Integer> deckToRemoveMap,
                             Map<String,Integer> sideboardToAddMap, Map<String,Integer> sideboardToRemoveMap,
                             DeckAccessLevelEnum deckAccessLevelEnum, String gamemode, String name)
    {
        Optional<Deck> optDeck = repository.findById(id);
        if(optDeck.isEmpty()) { throw new UnrecognizedDeckException(id);}
        Deck deck = optDeck.get();
        deck.setAccessLevel(deckAccessLevelEnum);
        deck.setGameMode(gamemode);
        deck.setName(name);
        List<Card> deckToAdd = new ArrayList<>();
        List<Card> sideboardToAdd = new ArrayList<>();
        List<Card> deckToRemove = new ArrayList<>();
        List<Card> sideboardToRemove = new ArrayList<>();
        if (!prepareAndValidateCardLists(deckToAddMap, deckToAdd))
            throw new UnrecognizedCardIdException("Unrecognized card");
        if (!prepareAndValidateCardLists(sideboardToAddMap, sideboardToAdd))
            throw new UnrecognizedCardIdException("Unrecognized card");
        if (!prepareAndValidateCardLists(deckToRemoveMap, deckToRemove))
            throw new UnrecognizedCardIdException("Unrecognized card");
        if (!prepareAndValidateCardLists(sideboardToRemoveMap, sideboardToRemove))
            throw new UnrecognizedCardIdException("Unrecognized card");
        List<Card> deckCards = deck.getCards();
        List<Card> sideboardCards = deck.getSideboard();
        deckCards.addAll(deckToAdd);
        deckCards.removeAll(deckToRemove);
        sideboardCards.addAll(sideboardToAdd);
        sideboardCards.removeAll(sideboardToRemove);
        deck.setCards(deckCards);
        deck.setSideboard(sideboardCards);
        //ADD DECK VALIDATION HERE//
        repository.save(deck);
        return getDeckByIdAndUserId(id,deck.getOwner().getId());
    }

    public void flushDatabase() {
        repository.deleteAll();
    }


    private boolean prepareAndValidateCardLists(Map<String, Integer> inputMap, List<Card> returnList) {
        for(String cardId: inputMap.keySet()) {
            if (!cardService.checkCardExistance(cardId)) {
                return false;
            }
            for (int j = 0 ; j < inputMap.get(cardId) ; j++) {
                returnList.add(cardService.getCard(cardId));
            }
        }
        return true;
    }
}

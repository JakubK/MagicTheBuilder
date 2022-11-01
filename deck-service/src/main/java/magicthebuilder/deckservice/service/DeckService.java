package magicthebuilder.deckservice.service;


import magicthebuilder.deckservice.dto.*;
import magicthebuilder.deckservice.entity.Card;
import magicthebuilder.deckservice.entity.Deck;
import magicthebuilder.deckservice.entity.User;
import magicthebuilder.deckservice.entity.enums.DeckAccessLevelEnum;
import magicthebuilder.deckservice.entity.enums.GameMode;
import magicthebuilder.deckservice.exception.customexceptions.InaccessibleDeckException;
import magicthebuilder.deckservice.exception.customexceptions.InaccessibleResourceException;
import magicthebuilder.deckservice.exception.customexceptions.UnrecognizedDeckException;
import magicthebuilder.deckservice.repository.DeckRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class DeckService {

    @Autowired
    private CardService cardService;
    @Autowired
    private DeckRepository repository;
    @Autowired
    private UserService userService;
    @Autowired
    private CollectionService collectionService;

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
        validateUserAccessToDeck(id,userId);
        return new DetailedDeckGetResponseDto(deck);

    }


    public List<SimpleDeckGetResponseDto> getPublicDecks(Pageable paging) {

        Page<Deck> decks = repository.findAllByAccessLevel(DeckAccessLevelEnum.PUBLIC, paging);
        return decks.stream()
                .map(SimpleDeckGetResponseDto::new)
                .toList();
    }

    public CardInDecksUsageDto getCardInDecksUsage(String cardId, Long userId) {
        CardInDecksUsageDto response = new CardInDecksUsageDto();
        response.setTotalOwnedAmount(collectionService.getCardAmountInCollection(cardId, userId));
        List<Deck> userDecks = getDecksByUserId(userId);
        List<CardInDeckStatsDto> cardInDecks = userDecks.stream()
                .map(deck -> new CardInDeckStatsDto(deck, cardId))
                .toList();
        response.setUsageInDecks(cardInDecks);
        return response;

    }

    public List<SimpleDeckGetResponseDto> getUserPublicDecks(Long userId) {
        return repository.findAllByAccessLevelAndOwner(DeckAccessLevelEnum.PUBLIC, userService.findById(userId)).stream()
                .map(SimpleDeckGetResponseDto::new)
                .toList();
    }

    public List<SimpleDeckGetResponseDto> getUserDecksList(Long userId, Pageable pageable) {
        Page<Deck> decks = repository.findAllByOwner(userService.findById(userId), pageable);
        return decks.stream()
                .map(SimpleDeckGetResponseDto::new)
                .toList();
    }


    public void saveDeck(Deck deck) {
        repository.save(deck);
    }

    public UUID createDeckFromDto(CreateDeckDto dto, long userId) {
        User owner = userService.findById(userId);
        Deck ret = CreateDeckDto.dtoToEntityMapper().apply(dto);
        ret.setOwner(owner);
        //validateDeck(ret);
        saveDeck(ret);
        return ret.getUuid();

    }

    public int addCardToDeck(UUID deckId,String cardId, Long userId) {
        Deck deck = getDeckById(deckId);
        validateUserAccessToDeck(deckId,userId);
        Card cardToAdd = cardService.getCardById(cardId);
        List<Card> deckCards = deck.getCards();
        deckCards.add(cardToAdd);
        deck.setCards(deckCards);
        saveDeck(deck);
        return amountOfCardInDeck(deckId, cardId);
    }

    public int addCardToSideboard(UUID deckId,String cardId, Long userId) {
        Deck deck = getDeckById(deckId);
        validateUserAccessToDeck(deckId,userId);
        Card cardToAdd = cardService.getCardById(cardId);
        List<Card> sideboardCards = deck.getSideboard();
        sideboardCards.add(cardToAdd);
        deck.setSideboard(sideboardCards);
        saveDeck(deck);
        return amountOfCardInSideboard(deckId,cardId);
    }

    public int removeCardFromDeck(UUID deckId,String cardId, Long userId) {
        Deck deck = getDeckById(deckId);
        validateUserAccessToDeck(deckId,userId);
        Card cardToRemove = cardService.getCardById(cardId);
        if(amountOfCardInDeck(deckId,cardId)==0) {
            return 0;
        } else {
            List<Card> deckCards = deck.getCards();
            deckCards.remove(cardToRemove);
            deck.setCards(deckCards);
            saveDeck(deck);
            return amountOfCardInDeck(deckId,cardId);
        }
    }

    public int removeCardFromSideboard(UUID deckId,String cardId, Long userId) {
        Deck deck = getDeckById(deckId);
        validateUserAccessToDeck(deckId,userId);
        Card cardToRemove = cardService.getCardById(cardId);
        if(amountOfCardInSideboard(deckId,cardId)==0) {
            return 0;
        } else {
            List<Card> sideboardCards = deck.getCards();
            sideboardCards.remove(cardToRemove);
            deck.setCards(sideboardCards);
            saveDeck(deck);
            return amountOfCardInDeck(deckId,cardId);
        }
    }

    public int setAmountOfCardInDeck(UUID deckId, CardInDeckAmountPutRequest dto, Long userId) {
        Deck deck = getDeckById(deckId);
        validateUserAccessToDeck(deckId,userId);
        Card cardToAdjust = cardService.getCardById(dto.getCardId());
        List<Card> deckCards = deck.getCards();
        int currentAmount = amountOfCardInDeck(deckId, cardToAdjust.getId());
        if(dto.getAmount() == 0){
            for(int i = currentAmount; i > 0 ; i--)
            {
                deckCards.remove(cardToAdjust);
            }
        }
        else if (dto.getAmount() > currentAmount) {
            for(int i = dto.getAmount(); i > currentAmount ; i--)
            {
                deckCards.add(cardToAdjust);
            }
        } else {
            for(int i = currentAmount; i > dto.getAmount() ; i--)
            {
                deckCards.remove(cardToAdjust);
            }
        }
        deck.setCards(deckCards);
        saveDeck(deck);
        return amountOfCardInDeck(deckId, cardToAdjust.getId());
    }

    public int setAmountOfCardInSideboard(UUID deckId, CardInDeckAmountPutRequest dto, Long userId) {
        Deck deck = getDeckById(deckId);
        validateUserAccessToDeck(deckId,userId);
        Card cardToAdjust = cardService.getCardById(dto.getCardId());
        List<Card> deckSideboard = deck.getSideboard();
        int currentAmount = amountOfCardInDeck(deckId, cardToAdjust.getId());
        if(dto.getAmount() == 0){
            for(int i = currentAmount; i > 0 ; i--)
            {
                deckSideboard.remove(cardToAdjust);
            }
        }
        else if (dto.getAmount() > currentAmount) {
            for(int i = dto.getAmount(); i > currentAmount ; i--)
            {
                deckSideboard.add(cardToAdjust);
            }
        } else {
            for(int i = currentAmount; i > dto.getAmount() ; i--)
            {
                deckSideboard.remove(cardToAdjust);
            }
        }
        deck.setSideboard(deckSideboard);
        saveDeck(deck);
        return amountOfCardInSideboard(deckId, cardToAdjust.getId());
    }

    public void updateDeckInfo(UUID deckId, DeckUpdateRequestDto dto, long userId) {
        Deck deck = getDeckById(deckId);
        validateUserAccessToDeck(deckId,userId);
        deck.setName(dto.getName());
        deck.setGameMode(dto.getGameMode());
        deck.setAccessLevel(dto.getAccessLevel());
        if(dto.getCommander() != null)
        {
            Card commander = cardService.getCardById(dto.getCommander());
            deck.setCommander(commander);
        }
    }

    public void deleteDeck(UUID deckId, long userId) {
        Deck deck = getDeckById(deckId);
        if (deck.getOwner().getId() == userId) {
            repository.deleteById(deckId);
        } else {
            throw new InaccessibleResourceException(userId);
        }
    }

    public DeckLegalityCheckResponseDto checkDeckLegality(UUID deckId, Long userId) {
        //extend this to check if deck is legal in selected gameMode in the future
        Deck deck = getDeckById(deckId);
        validateUserAccessToDeck(deckId,userId);
        DeckLegalityCheckRequestDto requestToValidationService = new DeckLegalityCheckRequestDto(deck);

        return new DeckLegalityCheckResponseDto();
    }
    public void flushDatabase() {
        repository.deleteAll();
    }


    private Deck getDeckById(UUID id) {
        Optional<Deck> optDeck = repository.findById(id);
        if (optDeck.isPresent()) {
            return optDeck.get();
        } else {
            throw new UnrecognizedDeckException(id);
        }
    }

    private List<Deck> getDecksByUserId(Long userId) {
        return repository.findAllByOwner(userService.findById(userId));
    }

    private void validateUserAccessToDeck(UUID deckId, Long userId)
    {
        Deck deck = getDeckById(deckId);
        if (!deck.getOwner().getId().equals(userId)) {
            throw new InaccessibleDeckException(deckId);
        }
    }

    private int amountOfCardInDeck(UUID deckId, String cardId)
    {
        Deck deck = getDeckById(deckId);
        return (int) deck.getCards().stream()
                .filter(card -> card.getId().equals(cardId))
                .count();
    }

    private int amountOfCardInSideboard(UUID deckId, String cardId)
    {
        Deck deck = getDeckById(deckId);
        return (int) deck.getSideboard().stream()
                .filter(card -> card.getId().equals(cardId))
                .count();
    }

}

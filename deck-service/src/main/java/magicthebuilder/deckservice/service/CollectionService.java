package magicthebuilder.deckservice.service;

import magicthebuilder.deckservice.ResponseBuilder.CollectionUpdateResponseBuilder;
import magicthebuilder.deckservice.dto.CollectionGetResponseDto;
import magicthebuilder.deckservice.dto.CollectionUpdateResponseDto;
import magicthebuilder.deckservice.dto.MultipleCardDto;
import magicthebuilder.deckservice.entity.Card;
import magicthebuilder.deckservice.entity.Collection;
import magicthebuilder.deckservice.entity.Deck;
import magicthebuilder.deckservice.entity.User;
import magicthebuilder.deckservice.entity.enums.CollectionAccessLevelEnum;
import magicthebuilder.deckservice.exception.customexceptions.UnrecognizedCardIdException;
import magicthebuilder.deckservice.exception.customexceptions.UnrecognizedUserIdException;
import magicthebuilder.deckservice.repository.CollectionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CollectionService {

    @Autowired
    CollectionRepository repository;

    @Autowired
    CardService cardService;


    public void add(Collection collection) {
        repository.save(collection);
    }

    public Optional<Collection> findById(Long id){
        return repository.findById(id);
    }

    public CollectionUpdateResponseDto updateCollection(Map<String,Integer> cardsToAdd, Map<String,Integer> cardsToRemove, Long userId,
                                   CollectionAccessLevelEnum accessLevelEnum) {
        CollectionUpdateResponseBuilder collUpdateResponseBuilder = new CollectionUpdateResponseBuilder();
        Optional<Collection> collOpt = findById(userId);
        if (collOpt.isPresent()) {
            Collection coll = collOpt.get();
            List<Card> toAdd = new ArrayList<>();
            List<Card> toRemove = new ArrayList<>();
            if (!prepareAndValidateCardLists(cardsToAdd, toAdd))
                throw new UnrecognizedCardIdException("Unrecognized card");
            if (!prepareAndValidateCardLists(cardsToRemove, toRemove))
                throw new UnrecognizedCardIdException("Unrecognized card");
            coll.cards.addAll(toAdd);
            coll.cards.removeAll(toRemove);
            coll.accessLevel=accessLevelEnum;
            this.add(coll);
            return collUpdateResponseBuilder.prepareValidCollectionUpdateResponse(userId);
        } else {
            throw new UnrecognizedUserIdException(userId);
        }
    }
    public CollectionGetResponseDto getCollectionById(Long userId) {
        Optional<Collection> collOpt = findById(userId);
        if (collOpt.isPresent()) {
            Collection coll = collOpt.get();
            CollectionGetResponseDto responseDto = new CollectionGetResponseDto();
            responseDto.setUserId(userId);
            responseDto.setAccessLevel(coll.accessLevel);
            Map<String, Integer> cardsFromCollection = new HashMap<>();
            List<MultipleCardDto> cardsInResponse = new ArrayList<>();

            for(Card card: coll.getCards()) {
                if(cardsFromCollection.containsKey(card.getId())) {
                    cardsFromCollection.put(card.getId(), cardsFromCollection.get(card.getId()) + 1);
                } else {
                    cardsFromCollection.put(card.getId(), 1);
                }
            }
            for (Map.Entry<String, Integer> pair : cardsFromCollection.entrySet()) {
                cardsInResponse.add(new MultipleCardDto(pair.getKey(),pair.getValue()));
            }
            responseDto.setCards(cardsInResponse);
            return responseDto;

        } else {
            throw new UnrecognizedUserIdException(userId);
        }
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

    public void flushDatabase()
    {
        repository.deleteAll();
    }



}

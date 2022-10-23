package magicthebuilder.deckservice.service;

import magicthebuilder.deckservice.dto.CollectionGetResponseDto;
import magicthebuilder.deckservice.dto.CollectionUpdateRequestDto;
import magicthebuilder.deckservice.dto.CollectionUpdateResponseDto;
import magicthebuilder.deckservice.dto.MultipleCardDto;
import magicthebuilder.deckservice.entity.Card;
import magicthebuilder.deckservice.entity.Collection;
import magicthebuilder.deckservice.entity.enums.CollectionAccessLevelEnum;
import magicthebuilder.deckservice.exception.customexceptions.InaccessibleResourceException;
import magicthebuilder.deckservice.exception.customexceptions.UnrecognizedCardIdException;
import magicthebuilder.deckservice.exception.customexceptions.UnrecognizedUserIdException;
import magicthebuilder.deckservice.repository.CollectionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CollectionService {

    @Autowired
    CollectionRepository repository;

    CardService cardService = new CardService();


    public void saveCollection(Collection collection) {
        repository.save(collection);
    }

    public Optional<Collection> findById(Long id) {
        return repository.findById(id);
    }

    public CollectionUpdateResponseDto updateCollection(CollectionUpdateRequestDto collectionDto, Long userId) {
        Collection coll = getCollectionById(userId);
        coll.setCards(getCardsFromCardMultipleCardDtoList(collectionDto.getCards()));
        coll.accessLevel = collectionDto.getAccessLevel();
        this.saveCollection(coll);
        return CollectionUpdateResponseDto.builder()
                .userId(userId)
                .details("Collection Updated")
                .build();
    }

    public CollectionGetResponseDto getOwnerCollection(Long userId) {
        Collection coll = getCollectionById(userId);
        return new CollectionGetResponseDto(coll);

    }

    public CollectionGetResponseDto getUserCollection(Long userId) {
        Collection coll = getCollectionById(userId);
        if (coll.getAccessLevel() != CollectionAccessLevelEnum.PRIVATE) {
            return new CollectionGetResponseDto(coll);
        } else {
            throw new InaccessibleResourceException(userId);
        }

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

    private Collection getCollectionById(Long userId) {
        Optional<Collection> collOpt = findById(userId);
        if (collOpt.isPresent()) {
            return collOpt.get();
        } else {
            throw new UnrecognizedUserIdException(userId);
        }
    }

    public void flushDatabase() {
        repository.deleteAll();
    }


}

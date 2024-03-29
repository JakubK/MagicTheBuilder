package magicthebuilder.deckservice.service;

import magicthebuilder.deckservice.dto.CollectionGetResponseDto;
import magicthebuilder.deckservice.entity.CardInCollection;
import magicthebuilder.deckservice.entity.Collection;
import magicthebuilder.deckservice.entity.enums.CollectionAccessLevelEnum;
import magicthebuilder.deckservice.exception.customexceptions.InaccessibleResourceException;
import magicthebuilder.deckservice.exception.customexceptions.UnrecognizedUserIdException;
import magicthebuilder.deckservice.repository.CardInCollectionRepository;
import magicthebuilder.deckservice.repository.CollectionRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CollectionService {

    private final CollectionRepository repository;

    private final CardInCollectionRepository cardInCollectionRepository;

    private final CardService cardService;

    public CollectionService(CardService _cardService, CardInCollectionRepository _cardInCollRepository, CollectionRepository _collectionRepository) {
        this.repository = _collectionRepository;
        this.cardInCollectionRepository = _cardInCollRepository;
        this.cardService = _cardService;
    }

    public void saveCollection(Collection collection) {
        repository.save(collection);
    }

    public Optional<Collection> findById(Long id) {
        return repository.findById(id);
    }

    public int getCardAmountInCollection(String cardId, Long userId) {
        Collection coll = getCollectionById(userId);
        Optional<CardInCollection> cardCollectionEntity = coll.getCards().stream()
                .filter(cardInColl -> cardInColl.getCard().getId().equals(cardId)).findFirst();
        return cardCollectionEntity.map(CardInCollection::getAmount).orElse(0);
    }

    public int addCardToCollection(String cardId, Long userId) {
        Collection coll = getCollectionById(userId);
        CardInCollection cardInCollection;
        Optional<CardInCollection> cardCollectionEntity = coll.getCards().stream()
                .filter(cardInColl -> cardInColl.getCard().getId().equals(cardId)).findFirst();
        if (cardCollectionEntity.isPresent()) {
            cardInCollection = cardCollectionEntity.get();
            int entityAmount = cardInCollection.getAmount();
            cardInCollection.setAmount(entityAmount + 1);
            cardInCollectionRepository.save(cardInCollection);
            saveCollection(coll);
        } else {
            cardInCollection = new CardInCollection(cardService.getCardById(cardId));
            List<CardInCollection> newCollection = coll.getCards();
            newCollection.add(cardInCollection);
            cardInCollectionRepository.save(cardInCollection);
            saveCollection(coll);
        }
        return cardInCollection.getAmount();
    }

    public int removeCardFromCollection(String cardId, Long userId) {
        Collection coll = getCollectionById(userId);
        List<CardInCollection> collectionCards = getCollectionById(userId).getCards();
        Optional<CardInCollection> cardCollectionEntity = collectionCards.stream()
                .filter(cardInColl -> cardInColl.getCard().getId().equals(cardId)).findFirst();
        if (cardCollectionEntity.isPresent()) {
            CardInCollection cardInCollection;
            cardInCollection = cardCollectionEntity.get();
            int entityAmount = cardInCollection.getAmount();
            if (entityAmount > 1) {
                cardInCollection.setAmount(entityAmount - 1);
                cardInCollectionRepository.save(cardInCollection);
                saveCollection(coll);
            } else {
                collectionCards.remove(cardInCollection);
                coll.setCards(collectionCards);
                saveCollection(coll);
                cardInCollectionRepository.delete(cardInCollection);
            }

            return entityAmount - 1;
        } else {
            return 0;
        }
    }

    public int setCardAmount(String cardId, int amount, Long userId) {
        Collection coll = getCollectionById(userId);
        List<CardInCollection> collectionCards = getCollectionById(userId).getCards();
        CardInCollection cardInCollection;
        Optional<CardInCollection> cardCollectionEntity = collectionCards.stream()
                .filter(cardInColl -> cardInColl.getCard().getId().equals(cardId)).findFirst();
        if (cardCollectionEntity.isPresent()) {
            cardInCollection = cardCollectionEntity.get();
            if (amount == 0) {
                collectionCards.remove(cardInCollection);
                coll.setCards(collectionCards);
                saveCollection(coll);
                cardInCollectionRepository.delete(cardInCollection);
                return 0;
            } else {
                cardInCollection.setAmount(amount);
                cardInCollectionRepository.save(cardInCollection);
                saveCollection(coll);
                return cardInCollection.getAmount();
            }
        } else {
            cardInCollection = new CardInCollection(cardService.getCardById(cardId));
            cardInCollection.setAmount(amount);
            List<CardInCollection> newCollection = coll.getCards();
            newCollection.add(cardInCollection);
            cardInCollectionRepository.save(cardInCollection);
            saveCollection(coll);
            return cardInCollection.getAmount();
        }
    }

    public CollectionGetResponseDto getOwnerCollection(Long userId, int page, int size) {
        Collection coll = getCollectionById(userId);
        return new CollectionGetResponseDto(coll, page, size);

    }

    public CollectionGetResponseDto getUserCollection(Long userId, int page, int size) {
        Collection coll = getCollectionById(userId);
        if (coll.getAccessLevel() != CollectionAccessLevelEnum.PRIVATE) {
            return new CollectionGetResponseDto(coll, page, size);
        } else {
            throw new InaccessibleResourceException(userId);
        }
    }

    public void updateCollectionAccessLevel(CollectionAccessLevelEnum accessLevelEnum, Long userId) {
        Collection coll = getCollectionById(userId);
        coll.setAccessLevel(accessLevelEnum);
        saveCollection(coll);
    }

    private Collection getCollectionById(Long userId) {
        Optional<Collection> collOpt = findById(userId);
        if (collOpt.isPresent()) {
            return collOpt.get();
        } else {
            throw new UnrecognizedUserIdException(userId);
        }
    }
}

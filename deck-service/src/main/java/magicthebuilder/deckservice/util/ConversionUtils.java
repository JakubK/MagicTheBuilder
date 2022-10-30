package magicthebuilder.deckservice.util;

import magicthebuilder.deckservice.dto.MultipleCardDto;
import magicthebuilder.deckservice.entity.Card;

import java.util.*;

public class ConversionUtils {

    public List<MultipleCardDto> CardListToMultipleCardsDto(List<Card> cards) {
        Map<String, Integer> cardsFromCollection = new HashMap<>();
        List<MultipleCardDto> cardsInResponse = new ArrayList<>();

        if (cards.isEmpty()) {
            return Collections.emptyList();
        }

        for (Card card : cards) {
            if (cardsFromCollection.containsKey(card.getId())) {
                cardsFromCollection.put(card.getId(), cardsFromCollection.get(card.getId()) + 1);
            } else {
                cardsFromCollection.put(card.getId(), 1);
            }
        }
        for (Map.Entry<String, Integer> pair : cardsFromCollection.entrySet()) {
            cardsInResponse.add(new MultipleCardDto(pair.getKey(), pair.getValue()));
        }
        return cardsInResponse;
    }
}

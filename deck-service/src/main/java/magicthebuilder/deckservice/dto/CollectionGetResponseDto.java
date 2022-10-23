package magicthebuilder.deckservice.dto;

import lombok.*;
import magicthebuilder.deckservice.entity.Card;
import magicthebuilder.deckservice.entity.Collection;
import magicthebuilder.deckservice.entity.enums.CollectionAccessLevelEnum;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class CollectionGetResponseDto {
    private Long userId;
    private CollectionAccessLevelEnum accessLevel;
    private List<MultipleCardDto> cards;

    public CollectionGetResponseDto(Collection coll) {
        setUserId(coll.userId);
        setAccessLevel(coll.accessLevel);
        Map<String, Integer> cardsFromCollection = new HashMap<>();
        List<MultipleCardDto> cardsInResponse = new ArrayList<>();

        for (Card card : coll.getCards()) {
            if (cardsFromCollection.containsKey(card.getId())) {
                cardsFromCollection.put(card.getId(), cardsFromCollection.get(card.getId()) + 1);
            } else {
                cardsFromCollection.put(card.getId(), 1);
            }
        }
        for (Map.Entry<String, Integer> pair : cardsFromCollection.entrySet()) {
            cardsInResponse.add(new MultipleCardDto(pair.getKey(), pair.getValue()));
        }
        setCards(cardsInResponse);
    }
}

package magicthebuilder.deckservice.dto;

import lombok.*;
import magicthebuilder.deckservice.entity.Collection;
import magicthebuilder.deckservice.entity.enums.CollectionAccessLevelEnum;

import java.util.Collections;
import java.util.List;

@Data
public class CollectionGetResponseDto {
    private Long userId;
    private CollectionAccessLevelEnum accessLevel;
    private List<MultipleCardDto> cards;

    public CollectionGetResponseDto(Collection coll, int page, int size) {
        setUserId(coll.userId);
        setAccessLevel(coll.accessLevel);

        List<MultipleCardDto> cardsInCollection = coll.getCards().stream()
                .map(MultipleCardDto::new)
                .toList();

        List<MultipleCardDto> cardsInResponse;
        if ((page * size) + size < cardsInCollection.size()) {
            cardsInResponse = cardsInCollection.subList(page * size, (page * size) + size);
        } else if (page * size < cardsInCollection.size()) {
            cardsInResponse = cardsInCollection.subList(page * size, cardsInCollection.size());
        } else {
            cardsInResponse = Collections.emptyList();
        }
        setCards(cardsInResponse);

    }
}

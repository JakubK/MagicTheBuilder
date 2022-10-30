package magicthebuilder.deckservice.dto;

import lombok.*;
import magicthebuilder.deckservice.entity.Card;
import magicthebuilder.deckservice.entity.Deck;
import magicthebuilder.deckservice.entity.enums.DeckAccessLevelEnum;
import magicthebuilder.deckservice.entity.enums.GameMode;

import java.util.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class DetailedDeckGetResponseDto {
    private UUID id;
    private String name;
    private Long ownerId;
    private GameMode gameMode;
    private DeckAccessLevelEnum accessLevel;
    private Date creationDate;
    private List<MultipleCardDto> cards;
    private List<MultipleCardDto> sideboard;
    private String commanderId = null;
    private boolean isValid;

    public DetailedDeckGetResponseDto(Deck deck) {
        this.id = deck.getUuid();
        this.name = deck.getName();
        this.ownerId = deck.getOwner().getId();
        this.gameMode = deck.getGameMode();
        this.accessLevel = deck.getAccessLevel();
        this.creationDate = deck.getCreationDate();
        this.cards = CardListToMultipleCardsDto(deck.getCards());
        this.sideboard = CardListToMultipleCardsDto(deck.getSideboard());
        if(deck.getCommander() != null) {
            this.commanderId = deck.getCommander().getId();
        }
        this.isValid = deck.isValid();
    }

    private List<MultipleCardDto> CardListToMultipleCardsDto(List<Card> cards) {
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


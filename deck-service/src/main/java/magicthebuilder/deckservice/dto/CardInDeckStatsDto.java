package magicthebuilder.deckservice.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import magicthebuilder.deckservice.entity.Deck;
import magicthebuilder.deckservice.entity.enums.GameMode;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class CardInDeckStatsDto {
    private UUID id;
    private String name;
    private GameMode gameMode;
    private Long usedAmount;


    public CardInDeckStatsDto(Deck deck, String cardId) {
        setId(deck.getUuid());
        setName(deck.getName());
        setGameMode(deck.getGameMode());
        Long usedAmount = deck.getCards().stream()
                .filter(card -> card.getId().equals(cardId))
                .count();
        setUsedAmount(usedAmount);

    }
}
package magicthebuilder.deckservice.dto;

import lombok.*;
import magicthebuilder.deckservice.entity.Card;
import magicthebuilder.deckservice.entity.Deck;
import magicthebuilder.deckservice.entity.enums.DeckAccessLevelEnum;
import magicthebuilder.deckservice.entity.enums.GameMode;
import magicthebuilder.deckservice.util.ConversionUtils;

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
        ConversionUtils utils = new ConversionUtils();
        this.id = deck.getUuid();
        this.name = deck.getName();
        this.ownerId = deck.getOwner().getId();
        this.gameMode = deck.getGameMode();
        this.accessLevel = deck.getAccessLevel();
        this.creationDate = deck.getCreationDate();
        this.cards = utils.CardListToMultipleCardsDto(deck.getCards());
        this.sideboard = utils.CardListToMultipleCardsDto(deck.getSideboard());
        if(deck.getCommander() != null) {
            this.commanderId = deck.getCommander().getId();
        }
        this.isValid = deck.isValid();
    }



}


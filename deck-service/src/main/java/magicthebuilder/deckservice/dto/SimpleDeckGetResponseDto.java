package magicthebuilder.deckservice.dto;

import lombok.*;
import magicthebuilder.deckservice.entity.Deck;
import magicthebuilder.deckservice.entity.enums.DeckAccessLevelEnum;
import magicthebuilder.deckservice.entity.enums.GameMode;

import java.util.Date;
import java.util.UUID;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class SimpleDeckGetResponseDto {

    private UUID id;
    private String name;
    private Long ownerId;
    private GameMode gameMode;
    private DeckAccessLevelEnum accessLevel;
    private Date creationDate;
    private int deckSize;
    private int sideboardSize;

    public SimpleDeckGetResponseDto(Deck deck) {
        this.id = deck.getUuid();
        this.name = deck.getName();
        this.gameMode = deck.getGameMode();
        this.ownerId = deck.getOwner().getId();
        this.accessLevel = deck.getAccessLevel();
        this.creationDate = deck.getCreationDate();
        this.deckSize = deck.getCards().size();
        this.sideboardSize = deck.getSideboard().size();
    }

}

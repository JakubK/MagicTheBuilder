package magicthebuilder.deckservice.dto;

import lombok.*;
import magicthebuilder.deckservice.entity.enums.DeckAccessLevelEnum;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class DeckUpdateRequestDto {
    private UUID id;
    private String name;
    private String gameMode;
    private List<MultipleCardDto> deckCardsToAdd;
    private List<MultipleCardDto> deckCardsToRemove;
    private List<MultipleCardDto> sideboardCardsToAdd;
    private List<MultipleCardDto> sideboardCardsToRemove;
    private DeckAccessLevelEnum accessLevel;
}

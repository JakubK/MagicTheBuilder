package magicthebuilder.deckservice.dto;

import lombok.*;
import magicthebuilder.deckservice.entity.enums.GameMode;
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
    private GameMode gameMode;
    private List<MultipleCardDto> deckCards;
    private List<MultipleCardDto> sideboardCards;
    private DeckAccessLevelEnum accessLevel;
}

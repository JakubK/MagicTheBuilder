package magicthebuilder.deckservice.dto;

import lombok.*;
import magicthebuilder.deckservice.entity.enums.DeckAccessLevelEnum;
import magicthebuilder.deckservice.entity.enums.GameMode;

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
    private String name;
    private GameMode gameMode;
    private DeckAccessLevelEnum accessLevel;
    private String commander = null;
}

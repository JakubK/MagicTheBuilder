package magicthebuilder.deckservice.dto;

import lombok.*;
import magicthebuilder.deckservice.entity.Deck;
import magicthebuilder.deckservice.entity.enums.DeckAccessLevelEnum;

import java.util.Collections;
import java.util.function.Function;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class CreateDeckDto {

    private String name;
    private String gameMode;
    private Long userId;
    private DeckAccessLevelEnum accessLevel;

    public static Function<CreateDeckDto, Deck> dtoToEntityMapper() {
        return request -> Deck.builder()
                .name(request.getName())
                .gameMode(request.getGameMode())
                .accessLevel(request.getAccessLevel())
                .cards(Collections.emptyList())
                .sideboard(Collections.emptyList())
                .build();
    }
}

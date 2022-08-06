package magicthebuilder.deckservice.dto;

import lombok.*;
import magicthebuilder.deckservice.entity.Deck;
import magicthebuilder.deckservice.entity.DeckAccessLevelEnum;
import java.util.List;
import java.util.UUID;
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
    private DeckAccessLevelEnum accessLevel;
    private List<String> cards;

    public static Function<CreateDeckDto, Deck> dtoToEntityMapper(){
        return request -> Deck.builder()
                .name(request.getName())
                .gameMode(request.getGameMode())
                .accessLevel(request.getAccessLevel())
                .cards(request.getCards())
                .build();
    }
}

package magicthebuilder.deckservice.dto;



import lombok.*;
import magicthebuilder.deckservice.entity.enums.CollectionAccessLevelEnum;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class CollectionUpdateRequestDto {

    private Long userId;
    private List<MultipleCardDto> cardsToAdd;
    private List<MultipleCardDto> cardsToRemove;
    private CollectionAccessLevelEnum accessLevel;


}

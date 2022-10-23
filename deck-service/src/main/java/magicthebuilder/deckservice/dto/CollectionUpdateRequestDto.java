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
    private List<MultipleCardDto> cards;
    private CollectionAccessLevelEnum accessLevel;
}

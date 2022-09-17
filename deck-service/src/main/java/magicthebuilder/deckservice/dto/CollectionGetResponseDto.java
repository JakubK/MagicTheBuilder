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
public class CollectionGetResponseDto {
    private Long userId;
    private CollectionAccessLevelEnum accessLevel;
    private List<MultipleCardDto> cards;
}

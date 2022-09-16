package magicthebuilder.deckservice.entity;

import lombok.*;
import magicthebuilder.deckservice.dto.MultipleCardDto;

import java.util.List;
@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class CollectionUpdateRequestEntity {
    private Long userId;
    private List<MultipleCardDto> cardToAdd;
    private List<MultipleCardDto> cardToRemove;
}

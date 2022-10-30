package magicthebuilder.deckservice.dto;

import com.sun.istack.NotNull;
import lombok.*;
import magicthebuilder.deckservice.entity.CardInCollection;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class MultipleCardDto {
    @NotNull
    private String cardId;
    private int amount;

    public MultipleCardDto(CardInCollection cardInColl) {
        this.cardId = cardInColl.getCard().getId();
        this.amount = cardInColl.getAmount();
    }
}

package magicthebuilder.deckservice.dto;

import com.sun.istack.NotNull;
import lombok.*;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class MultipleCardDto {
    @NotNull
    private String cardId;

    @NotNull
    private int amount;
}

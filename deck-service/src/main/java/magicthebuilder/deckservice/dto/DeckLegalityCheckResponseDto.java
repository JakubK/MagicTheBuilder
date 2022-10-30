package magicthebuilder.deckservice.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class DeckLegalityCheckResponseDto {
    private String result; // OK / ERROR
    private String errors = null;
}

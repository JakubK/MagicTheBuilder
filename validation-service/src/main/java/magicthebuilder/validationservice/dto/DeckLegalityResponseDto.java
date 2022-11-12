package magicthebuilder.validationservice.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DeckLegalityResponseDto {
    private List<String> brokenRules;
    private Boolean deckValid;
}

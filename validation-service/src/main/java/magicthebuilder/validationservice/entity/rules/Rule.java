package magicthebuilder.validationservice.entity.rules;

import magicthebuilder.validationservice.dto.DeckLegalityCheckDto;
import magicthebuilder.validationservice.entity.MtgCard;

import java.util.Map;

@FunctionalInterface
public interface Rule {
    public String Check(DeckLegalityCheckDto deckInfo, Map<String, MtgCard> cards);
}

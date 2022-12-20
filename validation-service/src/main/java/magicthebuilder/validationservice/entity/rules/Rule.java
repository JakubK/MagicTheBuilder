package magicthebuilder.validationservice.entity.rules;

import magicthebuilder.cardservice.entity.MtgCard;
import magicthebuilder.validationservice.dto.DeckLegalityCheckDto;

import java.util.Map;

@FunctionalInterface
public interface Rule {
    public String Check(DeckLegalityCheckDto deckInfo, Map<String, MtgCard> cards);
}

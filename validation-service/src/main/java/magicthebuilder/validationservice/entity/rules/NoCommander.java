package magicthebuilder.validationservice.entity.rules;

import magicthebuilder.validationservice.dto.DeckLegalityCheckDto;
import magicthebuilder.validationservice.entity.MtgCard;

import java.util.Map;

public class NoCommander implements Rule {
    @Override
    public String Check(DeckLegalityCheckDto deckInfo, Map<String, MtgCard> cards) {
        if (deckInfo.getCommander() == null)
            return null;

        return "Commander is illegal in this format";
    }
}

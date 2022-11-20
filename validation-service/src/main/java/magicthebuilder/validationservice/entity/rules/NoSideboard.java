package magicthebuilder.validationservice.entity.rules;

import magicthebuilder.cardservice.entity.MtgCard;
import magicthebuilder.validationservice.dto.DeckLegalityCheckDto;

import java.util.Map;

public class NoSideboard implements Rule {

    @Override
    public String Check(DeckLegalityCheckDto deckInfo, Map<String, MtgCard> cards) {
        if (deckInfo.getSideBoard() == null || deckInfo.getSideBoard().isEmpty())
            return null;

        return "Sideboard should be empty";
    }
}

package magicthebuilder.validationservice.entity.rules;

import magicthebuilder.validationservice.dto.DeckLegalityCheckDto;
import magicthebuilder.validationservice.dto.MultipleCardDto;
import magicthebuilder.validationservice.entity.MtgCard;

import java.util.Map;

public class SideboardNotLargerThanFifteen implements Rule {
    @Override
    public String Check(DeckLegalityCheckDto deckInfo, Map<String, MtgCard> cards) {
        int cardCount = deckInfo.getSideBoard().stream().mapToInt(MultipleCardDto::getAmount).sum();
        return cardCount <= 15 ? null : "There are more than " + cardCount + " cards in the sideboard. Maximum allowed is 15.";
    }
}

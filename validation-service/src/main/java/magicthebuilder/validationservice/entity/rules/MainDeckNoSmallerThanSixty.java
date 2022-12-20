package magicthebuilder.validationservice.entity.rules;

import magicthebuilder.cardservice.entity.MtgCard;
import magicthebuilder.validationservice.dto.DeckLegalityCheckDto;
import magicthebuilder.validationservice.dto.MultipleCardDto;

import java.util.Map;

public class MainDeckNoSmallerThanSixty implements Rule {

    @Override
    public String Check(DeckLegalityCheckDto deckInfo, Map<String, MtgCard> cards) {
        int cardCount = deckInfo.getDeck().stream().mapToInt(MultipleCardDto::getAmount).sum();
        return cardCount >= 60 ? null : "There are " + cardCount + " cards in the main deck. Minimum allowed is 60.";
    }
}

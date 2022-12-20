package magicthebuilder.validationservice.entity.rules;

import magicthebuilder.cardservice.entity.MtgCard;
import magicthebuilder.validationservice.dto.DeckLegalityCheckDto;
import magicthebuilder.validationservice.dto.MultipleCardDto;

import java.util.Map;

public class ExactlyNCards implements Rule {
    private final int n;

    public ExactlyNCards(int n) {
        this.n = n;
    }

    @Override
    public String Check(DeckLegalityCheckDto deckInfo, Map<String, MtgCard> cards) {
        int cardCount = deckInfo.getDeck().stream().mapToInt(MultipleCardDto::getAmount).sum();
        cardCount += deckInfo.getSideBoard().stream().mapToInt(MultipleCardDto::getAmount).sum();
        if (deckInfo.getCommander() != null) cardCount++;

        if (cardCount != n)
            return "Should be " + n + " cards. Instead there are " + cardCount;

        return null;
    }
}

package magicthebuilder.validationservice.entity.rules;

import magicthebuilder.cardservice.entity.MtgCard;
import magicthebuilder.validationservice.dto.DeckLegalityCheckDto;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class ColorIdentitySameAsCommander implements Rule {
    @Override
    public String Check(DeckLegalityCheckDto deckInfo, Map<String, MtgCard> cards) {
        if (deckInfo.getCommander() == null)
            return null;

        String errMessage = null;

        var commanderColors = Arrays.asList(cards.get(deckInfo.getCommander()).getColorIdentity());

        var otherCards = deckInfo.getDeck().stream().map(cI -> cards.get(cI.getCardId())).toList();

        for (var c : otherCards) {
            if (commanderColors.containsAll(List.of(c.getColorIdentity())))
                continue;

            if (errMessage == null) {
                errMessage = "Card " + c.getId() + " has color/s which doesn't occur in commander\n";
            } else {
                errMessage += "Card " + c.getId() + " has color/s which doesn't occur in commander\n";
            }
        }

        return errMessage;
    }
}

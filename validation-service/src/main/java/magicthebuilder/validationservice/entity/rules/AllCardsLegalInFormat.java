package magicthebuilder.validationservice.entity.rules;

import io.magicthegathering.javasdk.resource.Legality;
import magicthebuilder.cardservice.entity.MtgCard;
import magicthebuilder.validationservice.dto.DeckLegalityCheckDto;
import magicthebuilder.validationservice.dto.MultipleCardDto;

import java.util.Arrays;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class AllCardsLegalInFormat implements Rule {

    @Override
    public String Check(DeckLegalityCheckDto deckInfo, Map<String, MtgCard> cards) {
        Set<String> allCardIds = deckInfo.getDeck().stream()
                .map(MultipleCardDto::getCardId)
                .collect(Collectors.toSet());

        allCardIds.addAll(deckInfo.getSideBoard().stream()
                .map(MultipleCardDto::getCardId)
                .collect(Collectors.toSet()));

        String errMessage = null;

        var allCards = allCardIds.stream().map(cards::get).toList();


        for (MtgCard card : allCards) {
            Legality legality = new Legality();
            legality.setFormat(deckInfo.getFormat());
            legality.setLegality("Legal");
            if (Arrays.stream(card.getLegalities()).noneMatch(legality::equals)) {
                String msg = "Card " + card.getName() + " is illegal in format: " + legality.getFormat() + "\n";
                errMessage = errMessage != null ? errMessage + msg : msg;
            }
        }

        return errMessage;
    }
}

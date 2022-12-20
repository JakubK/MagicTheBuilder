package magicthebuilder.validationservice.entity.rules;

import magicthebuilder.cardservice.entity.MtgCard;
import magicthebuilder.validationservice.dto.DeckLegalityCheckDto;
import magicthebuilder.validationservice.dto.MultipleCardDto;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class NoMoreThanNNonBasicLandCopies implements Rule {

    private int maxCopies;

    public NoMoreThanNNonBasicLandCopies(int maxCopies) {
        this.maxCopies = maxCopies;
    }

    @Override
    public String Check(DeckLegalityCheckDto deckInfo, Map<String, MtgCard> cards) {
        String errMessage = null;

        var allCardsStats = deckInfo.getDeck()
                .stream()
                .collect(Collectors.toMap(MultipleCardDto::getCardId, MultipleCardDto::getAmount));

        for (MultipleCardDto cardInfo : deckInfo.getSideBoard())
            allCardsStats.merge(cardInfo.getCardId(), cardInfo.getAmount(), Integer::sum);

        for (var cardInfo : allCardsStats.entrySet()) {
            if (cards.get(cardInfo.getKey()).getRarity().equals("Basic Land"))
                continue;

            if (cardInfo.getValue() > maxCopies)
                errMessage = extendErrMessage(errMessage, cardInfo.getKey(), cardInfo.getValue());
        }

        return errMessage;
    }

    private String extendErrMessage(String errMessage, String cardId, int count) {
        String initialMessage = "There cannot be more than " + maxCopies + " copy/ies of the same card.\n";
        if (errMessage == null)
            errMessage = initialMessage;

        errMessage = errMessage + "Card: " + cardId + " has " + count + " copies.\n";
        return errMessage;
    }

    private List<MultipleCardDto> getSideboardCopies(DeckLegalityCheckDto deckInfo, String cardId) {
        return deckInfo.getSideBoard()
                .stream()
                .filter(cardStat -> Objects.equals(cardStat.getCardId(), cardId))
                .collect(Collectors.toList());
    }
}

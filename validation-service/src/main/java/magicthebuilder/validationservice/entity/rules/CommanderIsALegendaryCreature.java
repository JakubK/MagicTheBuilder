package magicthebuilder.validationservice.entity.rules;

import magicthebuilder.cardservice.entity.MtgCard;
import magicthebuilder.validationservice.dto.DeckLegalityCheckDto;

import java.util.Arrays;
import java.util.Map;

public class CommanderIsALegendaryCreature implements Rule {
    @Override
    public String Check(DeckLegalityCheckDto deckInfo, Map<String, MtgCard> cards) {
        if (deckInfo.getCommander() == null)
            return "Commander must be specified";

        var commander = cards.get(deckInfo.getCommander());
        if (commander.getTypes() == null || commander.getSupertypes() == null)
            return "Commander must be a legendary Creature";


        if (!Arrays.asList(commander.getTypes()).contains("Creature"))
            if (!Arrays.asList(commander.getSupertypes()).contains("Legendary"))
                return "Commander must be a legendary Creature";

        return null;
    }
}

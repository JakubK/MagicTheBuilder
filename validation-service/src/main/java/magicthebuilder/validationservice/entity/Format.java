package magicthebuilder.validationservice.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import magicthebuilder.cardservice.entity.MtgCard;
import magicthebuilder.validationservice.dto.DeckLegalityCheckDto;
import magicthebuilder.validationservice.entity.rules.Rule;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class Format {
    @Getter
    private final String name;
    private final List<Rule> rules = new ArrayList<>();

    public List<String> validate(DeckLegalityCheckDto deckInfo, Map<String, MtgCard> cards) {
        return rules.stream().map(rule -> rule.Check(deckInfo, cards)).collect(Collectors.toList());
    }

    public void AddRule(Rule rule) {
        rules.add(rule);
    }
}

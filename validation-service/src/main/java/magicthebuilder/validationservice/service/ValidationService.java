package magicthebuilder.validationservice.service;

import magicthebuilder.validationservice.dto.DeckLegalityCheckDto;
import magicthebuilder.validationservice.entity.Format;
import magicthebuilder.validationservice.entity.MtgCard;
import magicthebuilder.validationservice.repository.CardRepository;
import magicthebuilder.validationservice.repository.FormatsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
public class ValidationService {
    private final Map<String, MtgCard> cards;
    private final List<Format> formats;

    @Autowired
    public ValidationService(CardRepository cardRepository, FormatsRepository formatsRepository) {
        this.cards = cardRepository.getCards();
        this.formats = formatsRepository.getFormats();
    }

    public List<String> validateDeck(DeckLegalityCheckDto deckInfo) {
        return formats.stream()
                .filter(f -> Objects.equals(f.getName(), deckInfo.getFormat()))
                .findFirst()
                .orElseThrow()
                .validate(deckInfo, cards)
                .stream()
                .filter(Objects::nonNull)
                .toList();
    }
}


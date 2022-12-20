package magicthebuilder.validationservice.controller;

import magicthebuilder.validationservice.dto.DeckLegalityCheckDto;
import magicthebuilder.validationservice.service.ValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("api/internal/decks")
public class ValidationController {
    private final ValidationService validationService;

    @Autowired
    public ValidationController(ValidationService validationService) {
        this.validationService = validationService;
    }

    @PostMapping("validate")
    public List<String> validateDeck(@RequestBody DeckLegalityCheckDto deckInfo) {
        if (deckInfo.getSideBoard() == null)
            deckInfo.setSideBoard(new ArrayList<>());
        if (deckInfo.getDeck() == null)
            deckInfo.setDeck(new ArrayList<>());

        return validationService.validateDeck(deckInfo);
    }
}

package magicthebuilder.validationservice.controller;

import magicthebuilder.validationservice.dto.DeckLegalityCheckDto;
import magicthebuilder.validationservice.dto.DeckLegalityResponseDto;
import magicthebuilder.validationservice.service.ValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
@RequestMapping("api/internal/decks")
public class ValidationController {
    private final ValidationService validationService;

    @Autowired
    public ValidationController(ValidationService validationService) {
        this.validationService = validationService;
    }

    @PostMapping("validate")
    public DeckLegalityResponseDto validateDeck(@RequestBody DeckLegalityCheckDto deckInfo) {
        if (deckInfo.getSideBoard() == null)
            deckInfo.setSideBoard(new ArrayList<>());
        if (deckInfo.getDeck() == null)
            deckInfo.setDeck(new ArrayList<>());

        var brokenRules = validationService.validateDeck(deckInfo);



        return DeckLegalityResponseDto.builder()
                .deckValid(brokenRules.size() == 0)
                .brokenRules(brokenRules)
                .build();
    }
}

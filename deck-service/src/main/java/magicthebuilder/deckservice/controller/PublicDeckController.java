package magicthebuilder.deckservice.controller;


import magicthebuilder.deckservice.dto.DetailedDeckGetResponseDto;
import magicthebuilder.deckservice.dto.SimpleDeckGetResponseDto;
import magicthebuilder.deckservice.entity.enums.GameMode;
import magicthebuilder.deckservice.service.CollectionService;
import magicthebuilder.deckservice.service.DeckService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/decks")
public class PublicDeckController {


    private final DeckService deckService;

    public PublicDeckController(DeckService _deckService) {
        deckService = _deckService;
    }

    @GetMapping("")
    public List<SimpleDeckGetResponseDto> getPublicDecks(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "20") Integer size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        return deckService.getPublicDecks(pageable);
    }

    @GetMapping("/user/{userId}/decks")
    public List<SimpleDeckGetResponseDto> getUserPublicDecks(@PathVariable("userId") Long userId) {
        return deckService.getUserPublicDecks(userId);
    }

    @GetMapping("/{deckID}")
    public DetailedDeckGetResponseDto getNotPrivateDeck(@PathVariable("deckID") UUID deckId) {
        return deckService.getNotPrivateDeck(deckId);
    }

    @GetMapping("/{deckID}/deck/{cardId}/amount")
    public Long getCardAmountInDeck(@PathVariable("deckID") UUID deckId, @PathVariable("cardId") String cardId) {
        return deckService.getCardAmountInDeck(deckId, cardId);
    }

    @GetMapping("/{deckID}/sideboard/{cardId}/amount")
    public Long getCardAmountInSideboard(@PathVariable("deckID") UUID deckId, @PathVariable("cardId") String cardId) {
        return deckService.getCardAmountInSideboard(deckId, cardId);
    }


}

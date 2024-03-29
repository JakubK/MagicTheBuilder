package magicthebuilder.deckservice.controller;


import magicthebuilder.deckservice.dto.*;
import magicthebuilder.deckservice.service.DeckService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/auth/decks")
public class AuthDeckController {

    private final DeckService deckService;

    public AuthDeckController(DeckService _deckService) {
        deckService = _deckService;
    }

    @PostMapping()
    public UUID addDeck(@RequestBody CreateDeckDto deckDto,
                        @RequestHeader("id") Long userId) {
        return deckService.createDeckFromDto(deckDto, userId);
    }

    @PostMapping("/{deckId}/add/deck/{cardId}")
    public int addCardToDeck(@PathVariable("cardId") String cardId,
                             @PathVariable("deckId") UUID deckId,
                             @RequestHeader(value = "id") Long userId) {
        return deckService.addCardToDeck(deckId, cardId, userId);
    }

    @PostMapping("/{deckId}/add/sideboard/{cardId}")
    public int addCardToSideboard(@PathVariable("cardId") String cardId,
                                  @PathVariable("deckId") UUID deckId,
                                  @RequestHeader(value = "id") Long userId) {
        return deckService.addCardToSideboard(deckId, cardId, userId);
    }

    @PostMapping("/{deckId}/remove/deck/{cardId}")
    public int removeCardFromDeck(@PathVariable("cardId") String cardId,
                                  @PathVariable("deckId") UUID deckId,
                                  @RequestHeader(value = "id") Long userId) {
        return deckService.removeCardFromDeck(deckId, cardId, userId);
    }

    @PostMapping("/{deckId}/remove/sideboard/{cardId}")
    public int removeCardFromSideboard(@PathVariable("cardId") String cardId,
                                       @PathVariable("deckId") UUID deckId,
                                       @RequestHeader(value = "id") Long userId) {
        return deckService.removeCardFromSideboard(deckId, cardId, userId);
    }

    @PutMapping("/{deckId}/cards/{cardId}/{amount}")
    public int putAmountOfCardInDeck(@PathVariable("deckId") UUID deckId,
                                     @PathVariable("cardId") String cardId,
                                     @PathVariable("amount") int amount,
                                     @RequestHeader(value = "id") Long userId) {
        return deckService.setAmountOfCardInDeck(deckId, cardId, amount, userId);
    }

    @PutMapping("/{deckId}/sideboard/{cardId}/{amount}")
    public int setAmountOfCardInSideboard(@PathVariable("deckId") UUID deckId,
                                          @PathVariable("cardId") String cardId,
                                          @PathVariable("amount") int amount,
                                          @RequestHeader(value = "id") Long userId) {
        return deckService.setAmountOfCardInSideboard(deckId, cardId, amount, userId);
    }

    @PutMapping("/{deckId}")
    public void updateDeckInfo(@PathVariable("deckId") UUID deckId,
                               @RequestBody DeckUpdateRequestDto dto,
                               @RequestHeader(value = "id") Long userId) {
        deckService.updateDeckInfo(deckId, dto, userId);
    }

    @GetMapping("/{deckId}")
    public DetailedDeckGetResponseDto getOwnerDeck(@PathVariable("deckId") UUID deckId,
                                                   @RequestHeader(value = "id", required = false) Long userId) {
        return deckService.getOwnersDeck(deckId, userId);
    }

    @GetMapping("")
    public List<SimpleDeckGetResponseDto> getOwnersDecks(@RequestHeader(value = "id") Long userId,
                                                         @RequestParam(defaultValue = "0") Integer page,
                                                         @RequestParam(defaultValue = "20") Integer size) {
        Pageable pageable = PageRequest.of(page, size);
        return deckService.getUserDecksList(userId, pageable);
    }

    @GetMapping("/withCard/{cardId}")
    public CardInDecksUsageDto getCardInUserDecksUsage(@PathVariable("cardId") String cardId,
                                                       @RequestHeader(value = "id") Long userId) {
        return deckService.getCardInDecksUsage(cardId, userId);
    }

    @GetMapping("/{deckId}/valid")
    public List<String> checkDeckLegality(@PathVariable("deckId") UUID deckId,
                                          @RequestHeader(value = "id") Long userId) {
        return deckService.checkAndSetLegality(deckId, userId);
    }

    @DeleteMapping("/{deckID}")
    public void DeleteDeck(@PathVariable("deckID") UUID deckId, @RequestHeader("id") Long userId) {
        deckService.deleteDeck(deckId, userId);
    }


}

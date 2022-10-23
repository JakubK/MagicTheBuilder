package magicthebuilder.deckservice.controller;


import magicthebuilder.deckservice.dto.CreateDeckDto;
import magicthebuilder.deckservice.dto.DeckUpdateRequestDto;
import magicthebuilder.deckservice.dto.DetailedDeckGetResponseDto;
import magicthebuilder.deckservice.dto.SimpleDeckGetResponseDto;
import magicthebuilder.deckservice.service.DeckService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@CrossOrigin
@RequestMapping("api/auth/decks")
public class AuthDeckController {

    @Autowired
    private DeckService deckService;

    @PostMapping()
    public UUID addDeck(@RequestBody CreateDeckDto deckDto,
                        @RequestHeader("id") Long userId) {
        return deckService.createDeckFromDto(deckDto, userId);
    }

    @PutMapping("/{deckID}")
    public DetailedDeckGetResponseDto updateDeck(@RequestBody DeckUpdateRequestDto deckDto,
                                                 @RequestHeader("id") Long userId) {
        return deckService.updateDeck(deckDto, userId);
    }

    @GetMapping("/{deckID}")
    public DetailedDeckGetResponseDto getOwnerDeck(@PathVariable("deckID") UUID deckId,
                                                   @RequestHeader(value = "id", required = false) Long userId) {
        return deckService.getOwnersDeck(deckId, userId);
    }

    @GetMapping("/myDecks")
    public List<SimpleDeckGetResponseDto> getOwnersDecks(@RequestHeader(value = "id") Long userId) {

        return deckService.getUserDecks(userId);

    }

    @DeleteMapping("/{deckID}")
    public void DeleteDeck(@PathVariable("deckID") UUID deckId,
                           @RequestHeader("id") Long userId) {
        deckService.deleteDeck(deckId, userId);

    }


}

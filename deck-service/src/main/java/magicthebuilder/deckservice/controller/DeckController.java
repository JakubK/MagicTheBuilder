package magicthebuilder.deckservice.controller;


import magicthebuilder.deckservice.dto.CreateDeckDto;
import magicthebuilder.deckservice.dto.DetailedDeckGetResponseDto;
import magicthebuilder.deckservice.dto.SimpleDeckGetResponseDto;
import magicthebuilder.deckservice.entity.Deck;
import magicthebuilder.deckservice.service.DeckService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("api/decks")
public class DeckController {

    @Autowired
    private DeckService deckService;

    @GetMapping("/admin")
    public List<SimpleDeckGetResponseDto> getAllDecks() {
        return deckService.getAllDecks();
    }

    @GetMapping("")
    public List<SimpleDeckGetResponseDto> getPublicDecks() {
        return deckService.getPublicDecks();
    }

    @GetMapping("/{userId}/{deckID}")
    public DetailedDeckGetResponseDto getUserDecks(@PathVariable("userId") Long userId, @PathVariable("deckID") UUID deckId) {
        return deckService.getDeckByIdAndUserId(deckId, userId);
    }

   @PostMapping
   public UUID add(@RequestBody CreateDeckDto deckDto)
   {
       Deck deck = deckService.createDeckFromDto(deckDto);
       return deckService.addDeck(deck);
   }





}

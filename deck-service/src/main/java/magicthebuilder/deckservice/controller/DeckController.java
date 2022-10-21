package magicthebuilder.deckservice.controller;


import magicthebuilder.deckservice.dto.CreateDeckDto;
import magicthebuilder.deckservice.dto.DeckUpdateRequestDto;
import magicthebuilder.deckservice.dto.DetailedDeckGetResponseDto;
import magicthebuilder.deckservice.dto.SimpleDeckGetResponseDto;
import magicthebuilder.deckservice.entity.Deck;
import magicthebuilder.deckservice.service.DeckService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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
    public List<SimpleDeckGetResponseDto> getPublicDecks(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "20") Integer size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        return deckService.getPublicDecks(pageable);
    }

    @GetMapping("/{userId}/{deckID}")
    public DetailedDeckGetResponseDto getUserDeck(@PathVariable("userId") Long userId, @PathVariable("deckID") UUID deckId) {
        return deckService.getDeckByIdAndUserId(deckId, userId);
    } // to be changed to use headers instead of path for userId after implementing authorization

    @GetMapping("/{userId}")
    public List<SimpleDeckGetResponseDto> getUserDecks(@PathVariable("userId") Long userId) {
        return deckService.getDecksByUserId(userId);
    }

    @PostMapping
    public UUID addDeck(@RequestBody CreateDeckDto deckDto) {
        Deck deck = deckService.createDeckFromDto(deckDto);
        return deckService.addDeck(deck);
    }

    @PutMapping("/{userId}/{deckID}")
    public DetailedDeckGetResponseDto updateDeck(@RequestBody DeckUpdateRequestDto deckDto) {
        return deckService.updateDeck(deckDto);
    }

    @DeleteMapping("/{deckID}")
    public void DeleteDeck(@PathVariable("deckID") UUID deckId) {
        //validate userId with deck owner
        deckService.deleteDeck(deckId);

    }


}

package magicthebuilder.deckservice.controller;


import magicthebuilder.deckservice.dto.*;
import magicthebuilder.deckservice.entity.Deck;
import magicthebuilder.deckservice.service.DeckService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.*;

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
   public UUID addDeck(@RequestBody CreateDeckDto deckDto)
   {
       Deck deck = deckService.createDeckFromDto(deckDto);
       return deckService.addDeck(deck);
   }

    @PostMapping("/{userId}/{deckID}")
    public DetailedDeckGetResponseDto updateDeck(@RequestBody DeckUpdateRequestDto deckDto) {
        Map<String, Integer> deckToAdd = new HashMap<>();
        for(MultipleCardDto card: deckDto.getDeckCardsToAdd()) {
            deckToAdd.put(card.getCardId(),card.getAmount());
        }
        Map<String, Integer> deckToRemove = new HashMap<>();
        for(MultipleCardDto card: deckDto.getDeckCardsToRemove()) {
            deckToRemove.put(card.getCardId(),card.getAmount());
        }
        Map<String, Integer> sideboardToAdd = new HashMap<>();
        for(MultipleCardDto card: deckDto.getSideboardCardsToAdd()) {
            sideboardToAdd.put(card.getCardId(),card.getAmount());
        }
        Map<String, Integer> sideboardToRemove = new HashMap<>();
        for(MultipleCardDto card: deckDto.getSideboardCardsToRemove()) {
            sideboardToRemove.put(card.getCardId(),card.getAmount());
        }
        return deckService.updateDeck(deckDto.getId(),deckToAdd,deckToRemove,sideboardToAdd,sideboardToRemove,
                deckDto.getAccessLevel(), deckDto.getGamemode(), deckDto.getName());
    }





}

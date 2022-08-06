package magicthebuilder.deckservice.controller;


import magicthebuilder.deckservice.dto.CreateDeckDto;
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


    @GetMapping("/getDecks")
    public String findDecks() {
        List<Deck> decks = deckService.findall();
        return decks.toString();
    }

    @GetMapping("/getPublicDecks")
    public String findPublicDecks() {
        List<Deck> decks = deckService.findallPublic();
        return decks.toString();
    }

    @GetMapping("/{deckID}")
    public String getDeck(@PathVariable("deckID") UUID id) {
        Optional<Deck> deck = deckService.findById(id);
        return deck.toString();
    }

    @GetMapping("{deckID}/addCard/{id}")
    public String test(@PathVariable("id") int id, @PathVariable("deckID") UUID deckID)
    {
         // TODO - adding card to existing deck
        //return "adding to deck - "+deckID+" a card with ID -"+id;
        return  "";
    }

   @PostMapping
    public ResponseEntity<Void> add(@RequestBody CreateDeckDto deckDto, UriComponentsBuilder builder)
   {
       Deck deck = CreateDeckDto.dtoToEntityMapper().apply(deckDto);
       deckService.addDeck(deck);

       return ResponseEntity.created(builder.pathSegment("api", "characters", "{id}")
               .buildAndExpand(deck.getName()).toUri()).build();
   }





}

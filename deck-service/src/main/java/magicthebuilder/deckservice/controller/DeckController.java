package magicthebuilder.deckservice.controller;


import org.springframework.ui.Model;
import magicthebuilder.deckservice.entity.Deck;
import magicthebuilder.deckservice.entity.User;
import magicthebuilder.deckservice.service.DeckService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
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

    @GetMapping("{deckID}/addCard/{id}")
    public String test(@PathVariable("id") int id, @PathVariable("deckID") UUID deckID)
    {
         // TODO - adding card to existing deck
        //return "adding to deck - "+deckID+" a card with ID -"+id;
        return  "";
    }

   // private final DeckService deckService;





}

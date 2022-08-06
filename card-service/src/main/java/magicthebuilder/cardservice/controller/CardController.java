package magicthebuilder.cardservice.controller;


import io.magicthegathering.javasdk.resource.Card;
import magicthebuilder.cardservice.service.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/cards")
public class CardController {
    private final CardService cardService;

    @Autowired
    public CardController(CardService cardService) {
        this.cardService = cardService;
    }

    @GetMapping
    public List<Card> getCards() {
        return cardService.getCards();
    }

//    @GetMapping("{id}")
//    public Card getCard(@PathVariable("id") String id) {
//        return cardService.getCard(id);
//    }

    @GetMapping("{ids}")
    public List<Card> getCard(@PathVariable("ids") List<String> ids) {
        return cardService.getCards(ids);
    }
}

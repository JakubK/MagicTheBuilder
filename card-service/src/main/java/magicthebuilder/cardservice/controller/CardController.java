package magicthebuilder.cardservice.controller;


import lombok.AllArgsConstructor;
import magicthebuilder.cardservice.entity.MtgCard;
import magicthebuilder.cardservice.service.CardService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestController
@RequestMapping("api/cards")
@AllArgsConstructor
public class CardController {
    private final CardService cardService;

//    @GetMapping
//    public List<MtgCard> getCards() {
//        return cardService.getCards();
//    }

    // TODO: This endpoint should be blocked in Gateway/Reverse Proxy
    // Otherwise huge DOS risk
    @GetMapping("all")
    public List<MtgCard> getAllCards() {
        return cardService.getAllCards();
    }

    @GetMapping("{id}")
    public MtgCard getCard(@PathVariable("id") String id) {
        return cardService.getCard(id).
                orElseThrow(() -> new ResponseStatusException(NOT_FOUND,
                        "Unable to find card with id: " + id));
    }

    @GetMapping()
    public List<MtgCard> getCards(
            @RequestParam(value = "id", required = false) List<String> ids,
            @RequestParam(value = "name", required = false) List<String> names,
            @RequestParam(value = "color", required = false) List<String> colors,
            @RequestParam(value = "type", required = false) List<String> types
    ) {
        return cardService.getCards(ids, names, colors, types);
    }
}

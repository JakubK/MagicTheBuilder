package magicthebuilder.cardservice.controller;


import lombok.AllArgsConstructor;
import magicthebuilder.cardservice.entity.MtgCard;
import magicthebuilder.cardservice.service.CardService;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@CrossOrigin
@RestController
@RequestMapping("api/cards")
@AllArgsConstructor
public class CardController {
    private final CardService cardService;


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
    public Page<MtgCard> getCards(
            @RequestParam(value = "ids", required = false) List<String> ids, // or
            @RequestParam(value = "phrase", required = false) String phrase,
            @RequestParam(value = "colors", required = false) List<String> colors,
            @RequestParam(value = "types", required = false) List<String> types,
            @RequestParam(value = "sets", required = false) List<String> sets,
            @RequestParam(value = "formats", required = false) List<String> formats,
            @RequestParam(value = "page", required = false) Integer page,
            @RequestParam(value = "size", required = false) Integer size,
            @RequestParam(value = "sortBy", required = false) String sortBy
    ) {
        // TODO: Pack it up in some class
        return cardService.getCards(ids, phrase, colors, types, sets, formats, sortBy, page, size);
    }
}

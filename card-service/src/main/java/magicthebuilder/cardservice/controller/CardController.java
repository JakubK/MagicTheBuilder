package magicthebuilder.cardservice.controller;


import lombok.AllArgsConstructor;
import magicthebuilder.cardservice.entity.MtgCard;
import magicthebuilder.cardservice.model.CardsRequestModelBuilder;
import magicthebuilder.cardservice.service.CardService;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestController
@RequestMapping("api/cards")
@AllArgsConstructor
public class CardController {
    private final CardService cardService;


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
        var params = new CardsRequestModelBuilder()
                .setIds(ids)
                .setPhrase(phrase)
                .setColors(colors)
                .setTypes(types)
                .setSets(sets)
                .setFormats(formats)
                .setPage(page)
                .setSize(size)
                .setSortBy(sortBy)
                .build();
        return cardService.getCards(params);
    }
}






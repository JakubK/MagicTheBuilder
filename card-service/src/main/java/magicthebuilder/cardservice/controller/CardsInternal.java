package magicthebuilder.cardservice.controller;

import lombok.AllArgsConstructor;
import magicthebuilder.cardservice.entity.MtgCard;
import magicthebuilder.cardservice.service.CardService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@AllArgsConstructor
@RestController
@RequestMapping("api/internal/cards")
public class CardsInternal {
    private final CardService cardService;

    @GetMapping("all")
    public List<MtgCard> getAllCards() {
        return cardService.getAllCards();
    }

    @GetMapping("ids")
    public List<String> getAllCardIds() {
        return cardService.getAllIds();
    }
}

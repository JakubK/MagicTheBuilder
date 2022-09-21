package magicthebuilder.deckservice.controller;


import magicthebuilder.deckservice.dto.CardImportRequestDto;
import magicthebuilder.deckservice.service.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/cards")
public class CardsController {

    @Autowired
    CardService cardService;

    @PostMapping()
    public String postCards(@RequestBody CardImportRequestDto cardImportDto) {
        cardService.addCards(cardImportDto.getCardImportList());
        return "202";
    }
}

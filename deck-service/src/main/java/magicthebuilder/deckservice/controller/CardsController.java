package magicthebuilder.deckservice.controller;


import magicthebuilder.deckservice.dto.CardImportRequestDto;
import magicthebuilder.deckservice.dto.CollectionUpdateRequestDto;
import magicthebuilder.deckservice.entity.Collection;
import magicthebuilder.deckservice.entity.User;
import magicthebuilder.deckservice.service.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

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

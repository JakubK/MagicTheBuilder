package magicthebuilder.deckservice.controller;

import magicthebuilder.deckservice.dto.CardInCollectionPutRequest;
import magicthebuilder.deckservice.dto.CollectionGetResponseDto;
import magicthebuilder.deckservice.service.CollectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/auth/collections")
public class AuthCollectionController {
    @Autowired
    CollectionService collectionService;

    @GetMapping("")
    public CollectionGetResponseDto getCollection(@RequestHeader(value = "id") Long userId) {
        return collectionService.getOwnerCollection(userId);
    }

    @GetMapping("/{cardId}")
    public int getAmountOfCardInCollection(@PathVariable("cardId") String cardId, @RequestHeader(value = "id") Long userId) {
        return collectionService.getCardAmountInCollection(cardId, userId);
    }

    @PostMapping("/add/{cardId}")
    public int addCardToCollection(@PathVariable("cardId") String cardId, @RequestHeader(value = "id") Long userId) {
        return collectionService.addCardToCollection(cardId, userId);
    }

    @PostMapping("/remove/{cardId}")
    public int removeCardFromCollection(@PathVariable("cardId") String cardId, @RequestHeader(value = "id") Long userId) {
        return collectionService.removeCardFromCollection(cardId, userId);
    }

    @PutMapping("")
    public void setAmountOfCardInCollection(@RequestBody CardInCollectionPutRequest dto, @RequestHeader(value = "id") Long userId) {
        collectionService.setCardAmount(dto, userId);
    }


}
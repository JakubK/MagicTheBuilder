package magicthebuilder.deckservice.controller;

import magicthebuilder.deckservice.dto.CollectionGetResponseDto;
import magicthebuilder.deckservice.entity.enums.CollectionAccessLevelEnum;
import magicthebuilder.deckservice.service.CollectionService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/auth/collections")
public class AuthCollectionController {

    private final CollectionService collectionService;

    public AuthCollectionController(CollectionService _collectionService) {
        collectionService = _collectionService;
    }

    @GetMapping("")
    public CollectionGetResponseDto getCollection(@RequestHeader(value = "id") Long userId,
                                                  @RequestParam(defaultValue = "0") Integer page,
                                                  @RequestParam(defaultValue = "20") Integer size
    ) {
        return collectionService.getOwnerCollection(userId, page, size);
    }

    @GetMapping("/{cardId}")
    public int getAmountOfCardInCollection(@PathVariable("cardId") String cardId,
                                           @RequestHeader(value = "id") Long userId) {
        return collectionService.getCardAmountInCollection(cardId, userId);
    }

    @PostMapping("/add/{cardId}") // adds single card to collection
    public int addCardToCollection(@PathVariable("cardId") String cardId,
                                   @RequestHeader(value = "id") Long userId) {
        return collectionService.addCardToCollection(cardId, userId);
    }

    @PutMapping("/{accessLevel}")
    public void setCollectionAccessLevel(@PathVariable("accessLevel") CollectionAccessLevelEnum accessLevel,
                                         @RequestHeader(value = "id") Long userId) {
        collectionService.updateCollectionAccessLevel(accessLevel, userId);
    }

    @PostMapping("/remove/{cardId}") //removes single card from collection
    public int removeCardFromCollection(@PathVariable("cardId") String cardId,
                                        @RequestHeader(value = "id") Long userId) {
        return collectionService.removeCardFromCollection(cardId, userId);
    }

    @PutMapping("/{cardId}/{amount}")
    public int putAmountOfCardInCollection(@PathVariable("cardId") String cardId,
                                           @PathVariable("amount") int amount,
                                           @RequestHeader(value = "id") Long userId) {
        return collectionService.setCardAmount( cardId, amount, userId);
    }


}
package magicthebuilder.deckservice.controller;

import magicthebuilder.deckservice.dto.CollectionGetResponseDto;
import magicthebuilder.deckservice.dto.CollectionUpdateRequestDto;
import magicthebuilder.deckservice.dto.CollectionUpdateResponseDto;
import magicthebuilder.deckservice.dto.MultipleCardDto;
import magicthebuilder.deckservice.entity.Collection;
import magicthebuilder.deckservice.entity.User;
import magicthebuilder.deckservice.service.CollectionService;
import magicthebuilder.deckservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("api/collections")
public class CollectionController {
    @Autowired
    CollectionService collectionService;

    @Autowired
    private UserService userService;

    @GetMapping("/{userId}")
    public CollectionGetResponseDto getCollection(@PathVariable("userId") Long id) {
        return collectionService.getCollectionById(id);
    }

    //TODO getting other user's collection if it's set to public

    @PostMapping("/{userId}")
    public CollectionUpdateResponseDto updateCollection(@RequestBody CollectionUpdateRequestDto deckDto) {
        Map<String, Integer> toAdd = new HashMap<>();
        for(MultipleCardDto card: deckDto.getCardsToAdd()) {
            toAdd.put(card.getCardId(),card.getAmount());
        }
        Map<String, Integer> toRemove = new HashMap<>();
        for(MultipleCardDto card: deckDto.getCardsToRemove()) {
            toRemove.put(card.getCardId(),card.getAmount());
        }
        return collectionService.updateCollection(toAdd,toRemove, deckDto.getUserId(), deckDto.getAccessLevel());
    }

}

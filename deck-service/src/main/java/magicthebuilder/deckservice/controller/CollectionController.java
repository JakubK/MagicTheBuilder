package magicthebuilder.deckservice.controller;

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
    public String getCollection(@PathVariable("userId") Long id) {
        Optional<User> user  = userService.findById(id);
        if(user.isPresent()){
            Optional<Collection> foundColl = collectionService.findById(user.get().getId());
            if(foundColl.isPresent()) {
                return foundColl.get().toString();
            }
        }
        return "aa";
    }

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

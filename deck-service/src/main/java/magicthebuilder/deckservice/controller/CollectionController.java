package magicthebuilder.deckservice.controller;

import magicthebuilder.deckservice.entity.Collection;
import magicthebuilder.deckservice.entity.Deck;
import magicthebuilder.deckservice.entity.User;
import magicthebuilder.deckservice.service.CollectionService;
import magicthebuilder.deckservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

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
            Optional<Collection> foundColl = collectionService.findById(user.get().getCollection().getId());
            if(foundColl.isPresent()) {
                return foundColl.get().toString();
            }
        }

        return "aa";
    }

}

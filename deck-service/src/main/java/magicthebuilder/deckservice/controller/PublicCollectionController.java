package magicthebuilder.deckservice.controller;

import magicthebuilder.deckservice.dto.CollectionGetResponseDto;
import magicthebuilder.deckservice.service.CollectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("api/collections")
public class PublicCollectionController {
    @Autowired
    CollectionService collectionService;

    @GetMapping("/{userId}")
    public CollectionGetResponseDto getCollection(@PathVariable("userId") Long id) {
        return collectionService.getUserCollection(id);
    }

}

package magicthebuilder.deckservice.controller;

import magicthebuilder.deckservice.dto.CollectionGetResponseDto;
import magicthebuilder.deckservice.dto.CollectionUpdateRequestDto;
import magicthebuilder.deckservice.dto.CollectionUpdateResponseDto;
import magicthebuilder.deckservice.service.CollectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/collections")
public class CollectionController {
    @Autowired
    CollectionService collectionService;

    @GetMapping("/{userId}")
    public CollectionGetResponseDto getCollection(@PathVariable("userId") Long id) {
        return collectionService.getCollectionById(id);
    }

    //TODO getting other user's collection if it's set to public

    @PutMapping("/{userId}")
    public CollectionUpdateResponseDto updateCollection(@RequestBody CollectionUpdateRequestDto collectionDto) {

        return collectionService.updateCollection(collectionDto);
    }

}

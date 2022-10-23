package magicthebuilder.deckservice.controller;

import magicthebuilder.deckservice.dto.CollectionGetResponseDto;
import magicthebuilder.deckservice.dto.CollectionUpdateRequestDto;
import magicthebuilder.deckservice.dto.CollectionUpdateResponseDto;
import magicthebuilder.deckservice.service.CollectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("api/auth/collections")
public class AuthCollectionController {
    @Autowired
    CollectionService collectionService;

    @GetMapping("")
    public CollectionGetResponseDto getCollection(@RequestHeader(value = "id") Long userId) {
        return collectionService.getOwnerCollection(userId);
    }

    @PutMapping("")
    public CollectionUpdateResponseDto updateCollection(@RequestBody CollectionUpdateRequestDto collectionDto, @RequestHeader(value = "id") Long userId) {

        return collectionService.updateCollection(collectionDto, userId);
    }

}
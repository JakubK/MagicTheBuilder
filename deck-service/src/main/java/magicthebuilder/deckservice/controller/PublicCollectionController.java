package magicthebuilder.deckservice.controller;

import magicthebuilder.deckservice.dto.CollectionGetResponseDto;
import magicthebuilder.deckservice.service.CollectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/collections")
public class PublicCollectionController {
    @Autowired
    CollectionService collectionService;

    @GetMapping("/{userId}")
    public CollectionGetResponseDto getCollection(@PathVariable("userId") Long id) {
        return collectionService.getUserCollection(id);
    }

}

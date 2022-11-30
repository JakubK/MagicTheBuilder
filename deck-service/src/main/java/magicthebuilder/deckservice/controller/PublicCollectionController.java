package magicthebuilder.deckservice.controller;

import magicthebuilder.deckservice.dto.CollectionGetResponseDto;
import magicthebuilder.deckservice.entity.Collection;
import magicthebuilder.deckservice.service.CollectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/collections")
public class PublicCollectionController {

    private final CollectionService collectionService;

    public PublicCollectionController(CollectionService _collectionService) {
        this.collectionService = _collectionService;
    }

    @GetMapping("/{userId}")
    public CollectionGetResponseDto getCollection(@PathVariable("userId") Long id,
                                                  @RequestParam(defaultValue = "0") Integer page,
                                                  @RequestParam(defaultValue = "20") Integer size) {
        return collectionService.getUserCollection(id, page, size);
    }

}

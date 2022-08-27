package magicthebuilder.deckservice.service;

import magicthebuilder.deckservice.entity.Collection;
import magicthebuilder.deckservice.entity.Deck;
import magicthebuilder.deckservice.entity.User;
import magicthebuilder.deckservice.repository.CollectionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class CollectionService {

    @Autowired
    CollectionRepository repository;

    public void add(Collection collection) {
        repository.save(collection);
    }

    public Optional<Collection> findById(Long id){
        return repository.findById(id);
    }

}

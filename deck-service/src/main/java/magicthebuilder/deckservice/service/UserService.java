package magicthebuilder.deckservice.service;

import magicthebuilder.deckservice.entity.Collection;
import magicthebuilder.deckservice.entity.Deck;
import magicthebuilder.deckservice.entity.User;
import magicthebuilder.deckservice.entity.enums.CollectionAccessLevelEnum;
import magicthebuilder.deckservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    CollectionService collectionService;

    public void add (User user) {
        userRepository.save(user);
        Collection coll = new Collection(user.id,CollectionAccessLevelEnum.PUBLIC, Collections.emptyList());
        collectionService.add(coll);
    }


    public Optional<User> findById(Long id){
        return userRepository.findById(id);
    }
    public void flushDatabase()
    {
        userRepository.deleteAll();
    }

    public List<User> findAll() { return userRepository.findAll();}
}

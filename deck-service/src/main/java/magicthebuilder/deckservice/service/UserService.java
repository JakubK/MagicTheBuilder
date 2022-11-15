package magicthebuilder.deckservice.service;

import magicthebuilder.deckservice.entity.Collection;
import magicthebuilder.deckservice.entity.User;
import magicthebuilder.deckservice.entity.enums.CollectionAccessLevelEnum;
import magicthebuilder.deckservice.exception.customexceptions.UnrecognizedUserIdException;
import magicthebuilder.deckservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    CollectionService collectionService;

    public void add(User user) {
        userRepository.save(user);
        Collection coll = new Collection(user.id, CollectionAccessLevelEnum.PUBLIC);
        collectionService.saveCollection(coll);
    }


    public User findById(Long id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            return user.get();
        } else {
            throw new UnrecognizedUserIdException(id);
        }
    }
    public List<User> findAll() {
        return userRepository.findAll();
    }
}

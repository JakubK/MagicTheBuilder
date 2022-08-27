package magicthebuilder.deckservice.service;

import magicthebuilder.deckservice.entity.Deck;
import magicthebuilder.deckservice.entity.User;
import magicthebuilder.deckservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    public void add (User user) {
        userRepository.save(user);
    }


    public Optional<User> findById(Long id){
        return userRepository.findById(id);
    }
}

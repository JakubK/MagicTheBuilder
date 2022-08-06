package magicthebuilder.deckservice.service;


import magicthebuilder.deckservice.entity.Deck;
import magicthebuilder.deckservice.entity.DeckAccessLevelEnum;
import magicthebuilder.deckservice.repository.DeckRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class DeckService {

    @Autowired
    private DeckRepository repository;

    public List<Deck> findall(){
        return (List<Deck>) repository.findAll();
    }
    public List<Deck> findallPublic(){
        return (List<Deck>) repository.findAllByAccessLevel(DeckAccessLevelEnum.PUBLIC);
    }
    public Optional<Deck> findById(UUID id){
        return repository.findById(id);
    }
    public void addDeck(Deck deck){
        repository.save(deck);
    }
    public void flushDatabase()
    {
        repository.deleteAll();
    }
}

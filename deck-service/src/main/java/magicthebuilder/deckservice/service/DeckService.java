package magicthebuilder.deckservice.service;


import magicthebuilder.deckservice.entity.Deck;
import magicthebuilder.deckservice.repository.DeckRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeckService {

    @Autowired
    private DeckRepository repository;

    public List<Deck> findall(){
        var decks = (List<Deck>) repository.findAll();
        return decks;
    }
    public List<Deck> findallPublic(){
        var decks = (List<Deck>) repository.findAllByisPrivate(false);
        return decks;
    }
    public void addDeck(Deck deck){
        repository.save(deck);
    }
    public void flushDatabase()
    {
        repository.deleteAll();
    }

}

package magicthebuilder.deckservice.service;


import magicthebuilder.deckservice.entity.Card;
import magicthebuilder.deckservice.repository.CardRepository;
import magicthebuilder.deckservice.repository.DeckRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CardService {

    @Autowired
    private CardRepository repository;

    public List<Card> findall(){
        var cards = (List<Card>) repository.findAll();
        return cards;
    }

    public void add(Card card){
        repository.save(card);
    }
    public void flushDatabase()
    {
        repository.deleteAll();
    }

}

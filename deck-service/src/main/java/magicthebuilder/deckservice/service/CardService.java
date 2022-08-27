package magicthebuilder.deckservice.service;

import magicthebuilder.deckservice.entity.Card;
import magicthebuilder.deckservice.entity.Deck;
import magicthebuilder.deckservice.repository.CardRepository;
import magicthebuilder.deckservice.repository.DeckRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CardService {

    @Autowired
    private CardRepository repository;

    public void addCard(Card card){
        repository.save(card);
    }
}

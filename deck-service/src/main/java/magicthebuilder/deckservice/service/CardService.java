package magicthebuilder.deckservice.service;

import magicthebuilder.deckservice.entity.Card;
import magicthebuilder.deckservice.repository.CardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CardService {

    @Autowired
    private CardRepository repository;

    public void addCard(Card card) {
        repository.save(card);
    }

    public Card getCard(String id) {
        return repository.findById(id).get();

    }

    public void addCards(List<String> cardIds) {
        List<Card> cards = new ArrayList<>();
        for (String cardId : cardIds) {
            cards.add(new Card(cardId));
        }
        repository.saveAll(cards);
    }

    public Boolean checkCardExistance(String id) {
        return repository.findById(id).isPresent();
    }

    public void flushDatabase() {
        repository.deleteAll();
    }
}

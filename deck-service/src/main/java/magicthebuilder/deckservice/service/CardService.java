package magicthebuilder.deckservice.service;

import magicthebuilder.deckservice.entity.Card;
import magicthebuilder.deckservice.exception.customexceptions.UnrecognizedCardIdException;
import magicthebuilder.deckservice.repository.CardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CardService {

    @Autowired
    private CardRepository repository;

    public Card getCardById(String id) {
        Optional<Card> card = repository.findById(id);
        if (card.isPresent()) {
            return card.get();
        } else {
            throw new UnrecognizedCardIdException(id);
        }

    }

    public void addCards(List<String> cardIds) {
        List<Card> cards = new ArrayList<>();
        for (String cardId : cardIds) {
            cards.add(new Card(cardId));
        }
        repository.saveAll(cards);
    }

}

package magicthebuilder.cardservice.service;

import io.magicthegathering.javasdk.resource.Card;
import magicthebuilder.cardservice.repository.InMemoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class CardService {
    private final InMemoryRepository inMemoryRepository;


    @Autowired
    public CardService(InMemoryRepository inMemoryRepository) {
        this.inMemoryRepository = inMemoryRepository;
    }

    public List<Card> getCards() {
        return inMemoryRepository.getAllCards().stream().toList().subList(0, 100);
    }

    public List<Card> getCards(List<String> ids) {
        List<Card> cards = new ArrayList<>();
        for (String id : ids)
            cards.add(inMemoryRepository.getCard(id));

        return cards;
    }

    public Card getCard(String id) {
        return inMemoryRepository.getCard(id);
    }
}

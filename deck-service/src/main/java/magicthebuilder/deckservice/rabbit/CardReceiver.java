package magicthebuilder.deckservice.rabbit;

import magicthebuilder.deckservice.entity.Card;
import magicthebuilder.deckservice.repository.CardRepository;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class CardReceiver {

//    @Autowired
//    public void setCardRepository(CardRepository cardRepository) {
//        this.cardRepository = cardRepository;
//    }
//
//    private CardRepository cardRepository;
//
//    @RabbitListener(queues = "#{deleteQueue.name}")
//    public void receive(List<String> newCards) {
//        cardRepository.saveAll(newCards.stream().map(Card::new).toList());
//        System.out.println("Got " + newCards.size() + " from rabbit");
//    }
}



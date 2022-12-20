package magicthebuilder.validationservice.rabbit;

import magicthebuilder.cardservice.entity.MtgCard;
import magicthebuilder.validationservice.repository.CardRepository;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class CardReceiver {

    @Autowired
    public void setCardRepository(CardRepository cardRepository) {
        this.cardRepository = cardRepository;
    }

    private CardRepository cardRepository;

    @RabbitListener(queues = "#{deleteQueue.name}")
    public void receive1(List<MtgCard> newCards) {
        var cards = cardRepository.getCards();
        newCards.forEach(c -> cards.put(c.getId(), c));
        System.out.println("Updated " + newCards.size() + " cards");
    }
}



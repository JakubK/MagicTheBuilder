package magicthebuilder.cardservice.rabbit;

import magicthebuilder.cardservice.entity.MtgCard;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.util.List;

public class CardIdPublisher {
    @Value("${cardIdExchangeName}")
    private String exchangeName;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void sendMsg(final List<String> cards) {
        new FanoutExchange(exchangeName);
        rabbitTemplate.convertAndSend(exchangeName, "", cards);
        System.out.println("Sent " + cards.size() + " to rabbit exchange.");
    }
}



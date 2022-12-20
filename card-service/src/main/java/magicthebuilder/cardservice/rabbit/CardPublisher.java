package magicthebuilder.cardservice.rabbit;

import io.magicthegathering.javasdk.resource.Card;
import lombok.RequiredArgsConstructor;
import magicthebuilder.cardservice.entity.MtgCard;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.util.List;

public class CardPublisher {
    @Value("${exchangeName}")
    private String exchangeName;


    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void sendMsg(final List<MtgCard> cards) {
        new FanoutExchange(exchangeName);
        rabbitTemplate.convertAndSend(exchangeName, "", cards);
        System.out.println("Sent " + cards.size() + " to rabbit exchange.");
    }
}



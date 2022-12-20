package magicTheBuilder.userservice.rabbit;

import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;

public class Sender {
    @Autowired
    private TopicExchange exchange;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void sendMsg(final Long userId) {
        rabbitTemplate.convertAndSend(exchange.getName(), "mtb.uid.foo", userId);
        System.out.println("Sent userID to deck-service. Id sent : " + userId);
    }
}

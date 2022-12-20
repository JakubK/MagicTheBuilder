package magicthebuilder.deckservice.rabbit;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
public class RabbitConfig {

    static final String topicExchangeName = "MTB";

    static final String queueName = "userSyncQueue";


    @Value("${cardIdExchangeName}")
    private String exchangeName;

    @Bean
    public Queue userSyncQueue() {
        return new Queue(queueName, false);
    }

    @Bean
    public TopicExchange exchange() {
        return new TopicExchange(topicExchangeName);
    }

    @Bean
    public Binding binding(Queue userSyncQueue, TopicExchange exchange) {
        return BindingBuilder.bind(userSyncQueue).to(exchange).with("mtb.uid.#");
    }

    @Bean
    public Receiver receiver() {
        return new Receiver();
    }

    @Bean
    public FanoutExchange exchangeCards() {
        return new FanoutExchange(exchangeName);
    }

    @Bean
    public Queue deleteQueue() {
        return new AnonymousQueue();
    }

    @Bean
    public Binding bindingCards(FanoutExchange exchangeCards, Queue deleteQueue) {
        return BindingBuilder.bind(deleteQueue).to(exchangeCards);
    }

    @Bean
    public CardReceiver receiverCards() {
        return new CardReceiver();
    }
}

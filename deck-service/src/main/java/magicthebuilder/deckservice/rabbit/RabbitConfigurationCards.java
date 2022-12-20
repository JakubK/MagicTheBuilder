package magicthebuilder.deckservice.rabbit;


import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfigurationCards {
//    @Value("${cardIdExchangeName}")
//    private String exchangeName;
//
//    @Bean
//    public FanoutExchange exchangeCards() {
//        return new FanoutExchange(exchangeName);
//    }
//
//    @Bean
//    public Queue deleteQueueCards() {
//        return new AnonymousQueue();
//    }
//
//    @Bean
//    public Binding bindingCards(FanoutExchange fanout, Queue deleteQueue) {
//        return BindingBuilder.bind(deleteQueue).to(fanout);
//    }
//
//    @Bean
//    public CardReceiver receiverCards() {
//        return new CardReceiver();
//    }
}

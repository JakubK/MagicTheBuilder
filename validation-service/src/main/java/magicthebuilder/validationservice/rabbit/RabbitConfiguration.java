package magicthebuilder.validationservice.rabbit;


import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfiguration {
    @Value("${exchangeName}")
    private String exchangeName;

    @Bean
    public FanoutExchange exchange() {
        return new FanoutExchange(exchangeName);
    }

    @Bean
    public Queue deleteQueue() {
        return new AnonymousQueue();
    }

    @Bean
    public Binding binding(FanoutExchange fanout, Queue deleteQueue) {
        return BindingBuilder.bind(deleteQueue).to(fanout);
    }

    @Bean
    public CardReceiver receiver() {
        return new CardReceiver();
    }

}

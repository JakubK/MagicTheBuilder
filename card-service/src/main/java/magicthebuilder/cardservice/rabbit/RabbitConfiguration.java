package magicthebuilder.cardservice.rabbit;



import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfiguration {
    @Bean
    public CardPublisher sender() {
        return new CardPublisher();
    }

    @Bean
    public CardIdPublisher senderIds() {
        return new CardIdPublisher();
    }
}

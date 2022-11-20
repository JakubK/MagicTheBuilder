package magicthebuilder.validationservice;

import magicthebuilder.cardservice.entity.MtgCard;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
public class MyConfiguration {

    @Bean
    public Map<String, MtgCard> myMap(){
        java.util.Map<String, MtgCard> map = new java.util.HashMap<String, MtgCard>();
        return map;
    }
}

package magicthebuilder.deckservice;

import magicthebuilder.deckservice.entity.Deck;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


@SpringBootApplication
public class DeckServiceApplication {

	public static void main(String[] args) {

		SpringApplication.run(DeckServiceApplication.class, args);
	}

}

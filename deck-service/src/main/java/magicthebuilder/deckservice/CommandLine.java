package magicthebuilder.deckservice;

import magicthebuilder.deckservice.service.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@SpringBootApplication
public class CommandLine implements CommandLineRunner {


    private final CardService cardService;
    @Value("${cardservice.url}")
    private String cardServiceUrl;

    public CommandLine(CardService cardService) {
        this.cardService = cardService;
    }

    @Override
    public void run(String... args) throws InterruptedException {

        if (true) {   // DO YOU WANT TO RE-FILL DATABASE
            while (fillCards() != HttpStatus.OK) {
                Thread.sleep(10000);
                System.out.println("Attempting to download cards from card-service");
            }
            System.out.println("DATABASE FILLED");
        }
    }

        // should annotation stay?
    public HttpStatus fillCards() {
        HttpStatus result = HttpStatus.I_AM_A_TEAPOT;

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setRequestFactory((new HttpComponentsClientHttpRequestFactory()));
        ResponseEntity<?> responseEntity;

        try {
            responseEntity = restTemplate.exchange(cardServiceUrl + "/api/internal/cards/ids", HttpMethod.GET, null, String[].class);
            List<String> cards = Arrays.stream((String[]) Objects.requireNonNull(responseEntity.getBody())).toList();
            cardService.addCards(cards);
            result = responseEntity.getStatusCode();
        } catch (Exception e) {
            System.out.println("Failed to download cards from card-service. Attempting again in 10s");
        }
        return result;

    }
}
package magicthebuilder.deckservice;

import magicthebuilder.deckservice.entity.Card;
import magicthebuilder.deckservice.entity.Deck;
import magicthebuilder.deckservice.entity.User;
import magicthebuilder.deckservice.entity.enums.DeckAccessLevelEnum;
import magicthebuilder.deckservice.entity.enums.GameMode;
import magicthebuilder.deckservice.service.CardService;
import magicthebuilder.deckservice.service.CollectionService;
import magicthebuilder.deckservice.service.DeckService;
import magicthebuilder.deckservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;


//    PLEASE IGNORE THIS CLASS IN CODE REVIEW, IT WILL BE DELETED AT THE END OF DEVELOPMENT, AND THE
//    CARDS LOADING LOGIC WILL BE MOVED TO OTHER APPLICATION RUNNER


@SpringBootApplication
public class CommandLine implements CommandLineRunner {

    @Autowired
    private DeckService deckService;

    @Autowired
    private CardService cardService;

    @Autowired
    private UserService userService;

    @Autowired
    private CollectionService collectionService;


    @Override
    public void run(String... args) throws InterruptedException {

        if (true) {   // DO YOU WANT TO RE-FILL DATABASE
            while (fillCards() != HttpStatus.OK) {  // busy-waiting, needs to be changed in the future
                Thread.sleep(10000);
                System.out.println("Trying again");
            }
            clearDatabase();
            prepareInitialData();

            Card card = cardService.getCardById("004adf9a-b59a-5d56-9093-df73b9929bb1");
            Card card2 = cardService.getCardById("4c1a672a-3089-563f-a852-9580a425dbf1");
            Card card3 = cardService.getCardById("93ee4f89-0cd1-50a1-9525-401e2fccd32a");
            Card card4 = cardService.getCardById("60122d6f-448b-5df8-ac2a-8f5a1e841278");
            List<Card> coll = new ArrayList<>();
            List<Card> coll2 = new ArrayList<>();
            coll.add(card);
            coll.add(card2);
            coll.add(card2);
            coll.add(card3);
            coll.add(card3);
            coll.add(card3);
            coll2.add(card4);
            coll2.add(card4);
            coll2.add(card4);
            coll2.add(card4);


            Deck test = new Deck("TEST_DECK_1", GameMode.DRAFT, userService.findById(1000001L), DeckAccessLevelEnum.PUBLIC, coll, coll2);
            Deck test2 = new Deck("TEST_DECK_2", GameMode.DRAFT, userService.findById(1000000L), DeckAccessLevelEnum.PUBLIC, Collections.emptyList(), coll2);

            deckService.addDeck(test2);
            deckService.addDeck(test);

            System.out.println(test.getCards());
        }
    }

    @Async
    HttpStatus fillCards() {
        HttpStatus result = HttpStatus.I_AM_A_TEAPOT;

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setRequestFactory((new HttpComponentsClientHttpRequestFactory()));

        String url = "http://localhost:8085/api/cards/ids";
        ResponseEntity<?> responseEntity;

        try {
            responseEntity = restTemplate.exchange(url, HttpMethod.GET, null, String[].class);
            List<String> cards = Arrays.stream((String[]) responseEntity.getBody()).toList();
            cardService.addCards(cards);
            result = responseEntity.getStatusCode();
        } catch (Exception e) {
            System.out.println("Nie udało sie pobrać kart z card-service. Za 10s kolejna próba");
        }
        return result;

    }

    private void prepareInitialData() {
        userService.add(new User(1000000L));
        userService.add(new User(1000001L));
    }

    private void clearDatabase() {
        deckService.flushDatabase();
        userService.flushDatabase();
        collectionService.flushDatabase();
    }

}
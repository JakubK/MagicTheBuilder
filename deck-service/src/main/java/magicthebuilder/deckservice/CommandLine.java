package magicthebuilder.deckservice;

import magicthebuilder.deckservice.dto.MtgCardDto;
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

        clearDatabase();
        prepareInitialData();


        while (fillCards() != HttpStatus.OK) {  // busy-waiting, needs to be changed in the future
            Thread.sleep(10000);
            System.out.println("Trying again");
        }
        Card card = cardService.getCard("1");
        List<Card> coll = new ArrayList<>();
        coll.add(card);
        coll.add(card);


        Deck test = new Deck("tescik", GameMode.DRAFT, userService.findById(1L).get(), DeckAccessLevelEnum.PUBLIC, coll);
        Deck test2 = new Deck("tescik 2342323232", GameMode.DRAFT, userService.findById(2L).get(), DeckAccessLevelEnum.PUBLIC, null);

        deckService.addDeck(test2);
        deckService.addDeck(test);

        System.out.println(test.getCards());
    }

    @Async
    HttpStatus fillCards() {
        HttpStatus result = HttpStatus.I_AM_A_TEAPOT;

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setRequestFactory((new HttpComponentsClientHttpRequestFactory()));

        String url = "http://localhost:8085/api/cards/all";
        ResponseEntity<?> responseEntity;

        try {
            responseEntity = restTemplate.exchange(url, HttpMethod.GET, null, MtgCardDto[].class);
            MtgCardDto[] cards = (MtgCardDto[]) responseEntity.getBody();
            List<String> cardIds = Arrays.stream(cards)
                    .map(card -> card.getId())
                    .toList();
            responseEntity.getStatusCode();
            cardService.addCards(cardIds);
            result = responseEntity.getStatusCode();
        } catch (Exception e) {
        }
        return result;

    }

    private void prepareInitialData() {
        for (int i = 1; i < 100; i++) {
            cardService.addCard(new Card(String.valueOf(i)));
            userService.add(new User((long) i));
        }
    }

    private void clearDatabase() {
        deckService.flushDatabase();
        userService.flushDatabase();
        collectionService.flushDatabase();
        cardService.flushDatabase();
    }

}
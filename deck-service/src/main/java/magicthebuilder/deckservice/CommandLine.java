package magicthebuilder.deckservice;

import magicthebuilder.deckservice.entity.Card;
import magicthebuilder.deckservice.entity.Deck;
import magicthebuilder.deckservice.entity.User;
import magicthebuilder.deckservice.entity.enums.GameMode;
import magicthebuilder.deckservice.entity.enums.DeckAccessLevelEnum;
import magicthebuilder.deckservice.service.CardService;
import magicthebuilder.deckservice.service.CollectionService;
import magicthebuilder.deckservice.service.DeckService;
import magicthebuilder.deckservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.List;

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
    public void run(String... args) {

        clearDatabase();
        prepareInitialData();

        Card card = cardService.getCard("1");
        List<Card> coll = new ArrayList<>();
        coll.add(card);
        coll.add(card);


        Deck test = new Deck("tescik", GameMode.DRAFT, userService.findById(1L).get(), DeckAccessLevelEnum.PUBLIC, coll);
        Deck test2 = new Deck("tescik 2342323232", GameMode.DRAFT, userService.findById(2L).get(), DeckAccessLevelEnum.NOT_PUBLIC, null);

        deckService.addDeck(test2);
        deckService.addDeck(test);

        System.out.println(test.getCards());
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
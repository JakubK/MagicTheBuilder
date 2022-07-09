package magicthebuilder.deckservice;

import magicthebuilder.deckservice.entity.Card;
import magicthebuilder.deckservice.entity.Deck;
import magicthebuilder.deckservice.service.CardService;
import magicthebuilder.deckservice.service.DeckService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@SpringBootApplication
public class CommandLine implements CommandLineRunner {


    @Autowired
    private DeckService Deckservice;
    @Autowired
    private CardService Cardservice;

    @Override
    public void run(String... args) throws Exception {
        Deckservice.flushDatabase();

        Card testCard = new Card(561624);
        Card testCard2 = new Card(123456);
        Card testCard3 = new Card(654321);
        Card testCard4 = new Card(321456);
        Cardservice.add(testCard);
        Cardservice.add(testCard2);
        Cardservice.add(testCard3);
        Cardservice.add(testCard4);
        List<Card> coll = new ArrayList<Card>();
        coll.add(testCard);
        coll.add(testCard2);
        coll.add(testCard3);
        coll.add(testCard4);
        Deck test = new Deck(UUID.randomUUID(),"tescik",false,"test", coll);
        Deck test2 = new Deck(UUID.randomUUID(),"tescik 2342323232",true,"test", null);
        Deckservice.addDeck(test);
        Deckservice.addDeck(test2);



    }

}
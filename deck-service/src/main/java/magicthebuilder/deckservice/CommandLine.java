package magicthebuilder.deckservice;

import magicthebuilder.deckservice.entity.Deck;
import magicthebuilder.deckservice.entity.DeckAccessLevelEnum;
import magicthebuilder.deckservice.service.DeckService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@SpringBootApplication
public class CommandLine implements CommandLineRunner {


    @Autowired
    private DeckService Deckservice;


    @Override
    public void run(String... args) {
        Deckservice.flushDatabase();
        List<String> coll = new ArrayList<>();
        coll.add("a");
        coll.add("b");
        coll.add("c");
        coll.add("d");
        Deck test = new Deck("tescik","test", DeckAccessLevelEnum.PUBLIC,coll);
        Deck test2 = new Deck("tescik 2342323232","test",DeckAccessLevelEnum.NOT_PUBLIC, null);
        Deckservice.addDeck(test);
        Deckservice.addDeck(test2);

        System.out.println(test.getCards());



    }

}
package magicthebuilder.cardservice.repository;

import io.magicthegathering.javasdk.resource.Card;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Repository
public class InMemoryRepository {
    private final List<Card> allCardsList;
    private final Map<String, Card> allCardsMap;
//    private final Map<Integer, Card> allCardsMapMID;

    @Autowired
    public InMemoryRepository(FileRepository fileRepository, RestRepository restRepository, @Value("${readFromFile}") Boolean readFromFile) {
        this.allCardsList = readFromFile ? fileRepository.getAllCards() : restRepository.getAll();
//        fileRepository.save(allCardsList, "allCards");
        allCardsMap = allCardsList.stream().collect(Collectors.toMap(Card::getId, Function.identity()));
//        allCardsMapMID = allCardsList.stream().collect(Collectors.toMap(Card::getMultiverseid, Function.identity()));
    }

    public List<Card> getAllCards() {
        return allCardsList;
    }

    public Card getCard(String id) {
        return allCardsMap.get(id);
    }
}

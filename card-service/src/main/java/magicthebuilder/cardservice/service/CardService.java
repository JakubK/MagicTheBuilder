package magicthebuilder.cardservice.service;

import lombok.AllArgsConstructor;
import magicthebuilder.cardservice.entity.MtgCard;
import magicthebuilder.cardservice.repository.CardRepository;
import magicthebuilder.cardservice.repository.InMemoryRepository;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CardService {
    private final CardRepository cardRepository;
    private final InMemoryRepository inMemoryRepository;
    private final MongoTemplate mongoTemplate;

//    public List<Card> getCards() {
//        return inMemoryRepository.getAllCards().stream().toList().subList(0, 100);
//    }
//
//    public List<Card> getCards(List<String> ids) {
//        List<Card> cards = new ArrayList<>();
//        for (String id : ids)
//            cards.add(inMemoryRepository.getCard(id));
//
//        return cards;
//    }
//
//    public Card getCard(String id) {
//        return inMemoryRepository.getCard(id);
//    }

    public Optional<MtgCard> getCard(String id) {
        return cardRepository.findById(id);
    }

    public List<MtgCard> getCards(List<String> ids, List<String> names, List<String> colors, List<String> types) {
        var query = new Query();
//        final List<Criteria> criteria = new ArrayList<>();

        if (ids != null && !ids.isEmpty())
            query.addCriteria(Criteria.where("id").in(ids));
        if (names != null && !names.isEmpty())
            query.addCriteria(Criteria.where("name").in(names));
        if (colors != null && !colors.isEmpty())
            query.addCriteria(Criteria.where("colors").is(colors));
        if (types != null && !types.isEmpty())
            query.addCriteria(Criteria.where("type").in(types));

//        if (!criteria.isEmpty())

        return mongoTemplate.find(query, MtgCard.class);
//        return StreamSupport
//                .stream(cardRepository.findAllById(Ids).spliterator(), true)
//                .collect(Collectors.toList());
    }

    public List<MtgCard> getAllCards() {
        return mongoTemplate.findAll(MtgCard.class);
    }


}

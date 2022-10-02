package magicthebuilder.cardservice.service;

import lombok.AllArgsConstructor;
import magicthebuilder.cardservice.entity.MtgCard;
import magicthebuilder.cardservice.repository.CardRepository;
import magicthebuilder.cardservice.repository.InMemoryRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.TextCriteria;
import org.springframework.data.support.PageableExecutionUtils;
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

    public Page<MtgCard> getCards(List<String> ids, String phrase, List<String> colors, List<String> types, Pageable pageable) {
        var query = new Query().with(pageable);
//        final List<Criteria> criteria = new ArrayList<>();

        if (ids != null && !ids.isEmpty())
            query.addCriteria(Criteria.where("id").in(ids));
        if (phrase != null && !phrase.isEmpty())
            query.addCriteria(TextCriteria.forLanguage("en").matching(phrase));
        if (colors != null && !colors.isEmpty()) {
            query.addCriteria(Criteria.where("colors").all(colors));
        }
        if (types != null && !types.isEmpty())
            query.addCriteria(Criteria.where("types").all(types));

//        if (!criteria.isEmpty())

//        return mongoTemplate.find(query, MtgCard.class);
        return PageableExecutionUtils.getPage(
                mongoTemplate.find(query, MtgCard.class),
                pageable,
                () -> mongoTemplate.count(query.skip(0).limit(0), MtgCard.class)
        );
//        return StreamSupport
//                .stream(cardRepository.findAllById(Ids).spliterator(), true)
//                .collect(Collectors.toList());
    }

    public List<MtgCard> getAllCards() {
        return mongoTemplate.findAll(MtgCard.class);
    }


}

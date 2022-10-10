package magicthebuilder.cardservice.service;

import lombok.AllArgsConstructor;
import magicthebuilder.cardservice.entity.MtgCard;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.TextCriteria;
import org.springframework.data.mongodb.core.query.TextQuery;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CardService {
    private final MongoTemplate mongoTemplate;

    public Optional<MtgCard> getCard(String id) {
        return Optional.ofNullable(
                mongoTemplate.findById(id, MtgCard.class)
        );
    }


    public Page<MtgCard> getCards(List<String> ids, String phrase, List<String> colors, List<String> types, Pageable pageable) {
        var query = new Query().with(pageable);

        if (phrase != null && !phrase.isEmpty())
            query = TextQuery
                    .queryText(TextCriteria.forLanguage("en").matching(phrase))
                    .sortByScore()
                    .with(pageable);

        if (ids != null && !ids.isEmpty())
            query.addCriteria(Criteria.where("id").in(ids));

        if (colors != null && !colors.isEmpty())
            query.addCriteria(Criteria.where("colors").all(colors));

        if (types != null && !types.isEmpty())
            query.addCriteria(Criteria.where("types").all(types));

        Query finalQuery = query;
        return PageableExecutionUtils.getPage(
                mongoTemplate.find(query, MtgCard.class),
                pageable,
                () -> mongoTemplate.count(finalQuery.skip(0).limit(0), MtgCard.class)
        );
    }

    public List<MtgCard> getAllCards() {
        return mongoTemplate.findAll(MtgCard.class);
    }


}

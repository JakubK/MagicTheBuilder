package magicthebuilder.cardservice.service;

import io.magicthegathering.javasdk.resource.Legality;
import lombok.AllArgsConstructor;
import magicthebuilder.cardservice.entity.MtgCard;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.TextCriteria;
import org.springframework.data.mongodb.core.query.TextQuery;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CardService {
    private final MongoTemplate mongoTemplate;

    public Optional<MtgCard> getCard(String id) {
        return Optional.ofNullable(
                mongoTemplate.findById(id, MtgCard.class)
        );
    }


    public Page<MtgCard> getCards(List<String> ids, String phrase, List<String> colors, List<String> types, List<String> sets, List<String> formats, String sortBy, Integer page, Integer size) {
        Pageable pageable = PageRequest.of(
                page != null ? page : 0,
                size != null ? size : 100,
                sortBy != null && !sortBy.isEmpty() ? Sort.by(sortBy) : Sort.unsorted());

        var query = prepareQuery(ids, phrase, colors, types, sets, formats, pageable);

        return PageableExecutionUtils.getPage(
                mongoTemplate.find(query, MtgCard.class),
                pageable,
                () -> mongoTemplate.count(query.skip(0).limit(0), MtgCard.class)
        );
    }

    private Query prepareQuery(List<String> ids, String phrase, List<String> colors, List<String> types, List<String> sets, List<String> formats, Pageable pageable) {
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

        if (sets != null && !sets.isEmpty())
            query.addCriteria(Criteria.where("setName").in(sets));

        if (formats != null && !formats.isEmpty()) {
            List<Legality> legalities = formats.stream().map(f -> {
                Legality l = new Legality();
                l.setFormat(f);
                l.setLegality("Legal");
                return l;
            }).collect(Collectors.toList());

            query.addCriteria(Criteria.where("legalities").all(legalities));
        }

        return query;
    }

    public List<MtgCard> getAllCards() {
        return mongoTemplate.findAll(MtgCard.class);
    }

    public List<String> getAllIds() {
        return mongoTemplate.findAll(MtgCard.class)
                .stream().map(MtgCard::getId).collect(Collectors.toList());
    }
}

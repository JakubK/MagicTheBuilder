package magicthebuilder.cardservice.service;

import io.magicthegathering.javasdk.resource.Legality;
import lombok.AllArgsConstructor;
import magicthebuilder.cardservice.entity.MtgCard;
import magicthebuilder.cardservice.model.CardsRequestModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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


    public Page<MtgCard> getCards(CardsRequestModel requestParams) {
        Pageable pageable = PageRequest.of(
                requestParams.getPage(),
                requestParams.getSize(),
                requestParams.getSortBy());

        var query = prepareQuery(requestParams, pageable);

        return PageableExecutionUtils.getPage(
                mongoTemplate.find(query, MtgCard.class),
                pageable,
                () -> mongoTemplate.count(query.skip(0).limit(0), MtgCard.class)
        );
    }

    private Query prepareQuery(CardsRequestModel requestParams, Pageable pageable) {
        var query = new Query().with(pageable);

        if (requestParams.getPhrase() != null)
            query = TextQuery
                    .queryText(TextCriteria.forLanguage("en").matching(requestParams.getPhrase()))
                    .sortByScore()
                    .with(pageable);

        if (requestParams.getIds() != null)
            query.addCriteria(Criteria.where("id").in(requestParams.getIds()));

        if (requestParams.getColors() != null)
            query.addCriteria(Criteria.where("colors").all(requestParams.getColors()));

        if (requestParams.getTypes() != null)
            query.addCriteria(Criteria.where("types").all(requestParams.getTypes()));

        if (requestParams.getSets() != null)
            query.addCriteria(Criteria.where("setName").in(requestParams.getSets()));

        if (requestParams.getFormats() != null) {
            List<Legality> legalities = requestParams.getFormats()
                    .stream().map(f -> {
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

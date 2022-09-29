package magicthebuilder.cardservice.repository;

import magicthebuilder.cardservice.entity.MtgCard;
import org.springframework.data.mongodb.repository.MongoRepository;


public interface CardRepository extends MongoRepository<MtgCard, String> {

}

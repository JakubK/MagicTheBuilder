package magicthebuilder.deckservice.repository;

import magicthebuilder.deckservice.entity.CardInCollection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CardInCollectionRepository extends JpaRepository<CardInCollection, String> {
}

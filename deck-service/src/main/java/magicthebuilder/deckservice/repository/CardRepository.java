package magicthebuilder.deckservice.repository;

import magicthebuilder.deckservice.entity.Card;
import magicthebuilder.deckservice.entity.Deck;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;
@Repository
public interface CardRepository extends JpaRepository<Card, String>  {
}

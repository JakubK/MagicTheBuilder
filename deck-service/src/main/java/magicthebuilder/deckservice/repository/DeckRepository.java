package magicthebuilder.deckservice.repository;


import magicthebuilder.deckservice.entity.Deck;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


import java.util.List;
import java.util.UUID;

@Repository
public interface DeckRepository extends JpaRepository<Deck, UUID> {


    List<Deck>  findAllByisPrivate(boolean isPriv);




}

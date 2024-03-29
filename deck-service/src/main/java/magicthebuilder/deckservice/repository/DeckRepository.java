package magicthebuilder.deckservice.repository;


import magicthebuilder.deckservice.entity.Deck;
import magicthebuilder.deckservice.entity.User;
import magicthebuilder.deckservice.entity.enums.DeckAccessLevelEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface DeckRepository extends JpaRepository<Deck, UUID> {


    Page<Deck> findAllByAccessLevel(DeckAccessLevelEnum accLevel, Pageable pageable);

    List<Deck> findAllByAccessLevelAndOwner(DeckAccessLevelEnum accLevel, User user);

    List<Deck> findAllByOwner(User userId);

    Page<Deck> findAllByOwner(User userId, Pageable pageable);


}

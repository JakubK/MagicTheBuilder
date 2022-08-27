package magicthebuilder.deckservice.repository;

import magicthebuilder.deckservice.entity.Collection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;
@Repository
public interface CollectionRepository extends JpaRepository<Collection, Long> {
}

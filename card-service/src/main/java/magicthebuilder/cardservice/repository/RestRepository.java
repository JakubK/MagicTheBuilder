package magicthebuilder.cardservice.repository;

import io.magicthegathering.javasdk.api.CardAPI;
import io.magicthegathering.javasdk.resource.Card;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class RestRepository {
    public List<Card> getAll() {
        return CardAPI.getAllCards().stream()
                .filter(c -> c.getImageUrl() != null)
                .toList();
    }
}

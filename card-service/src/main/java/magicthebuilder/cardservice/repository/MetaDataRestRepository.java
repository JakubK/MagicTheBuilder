package magicthebuilder.cardservice.repository;

import io.magicthegathering.javasdk.api.CardAPI;
import io.magicthegathering.javasdk.api.SetAPI;
import io.magicthegathering.javasdk.resource.MtgSet;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;

@Repository
public class MetaDataRestRepository {
    public List<String> getTypes() {
        return CardAPI.getAllCardTypes();
    }

    public List<MtgSet> getSets() {
        return SetAPI.getAllSets();
    }

    public List<String> getFormats() {
        return Arrays.asList("Standard", "Modern", "Commander");
    }
}

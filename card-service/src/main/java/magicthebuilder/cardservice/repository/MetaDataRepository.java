package magicthebuilder.cardservice.repository;

import io.magicthegathering.javasdk.api.CardAPI;
import io.magicthegathering.javasdk.api.SetAPI;
import io.magicthegathering.javasdk.resource.MtgSet;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;

@Repository
public class MetaDataRepository {
    private List<String> types = downloadTypes();

    private List<String> formats = downloadFormats();
    private List<MtgSet> sets = downloadSets();

    public List<String> getFormats() {
        return formats == null ? downloadFormats() : formats;
    }

    public List<MtgSet> getSets() {
        return sets == null ? downloadSets() : sets;
    }

    public List<String> getTypes() {
        return types == null ? downloadTypes() : types;
    }

    @Scheduled(cron = "* */2 * * * *")
    public void updateData() {
        types = downloadTypes();
        sets = downloadSets();
        formats = downloadFormats();
    }

    private List<MtgSet> downloadSets() {
        return  SetAPI.getAllSets();
    }

    private List<String> downloadTypes() {
        return CardAPI.getAllCardTypes();
    }

    // For now store only supported formats
    private List<String> downloadFormats() {
        return Arrays.asList("Standard", "Modern", "Commander");
    }

}

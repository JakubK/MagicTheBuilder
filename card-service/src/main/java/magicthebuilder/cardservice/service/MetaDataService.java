package magicthebuilder.cardservice.service;

import io.magicthegathering.javasdk.resource.MtgSet;
import lombok.AllArgsConstructor;
import magicthebuilder.cardservice.repository.MetaDataRestRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class MetaDataService {
    private final MetaDataRestRepository metaDataRestRepository;

    public List<String> getTypes() {
        return metaDataRestRepository.getTypes();
    }

    public List<MtgSet> getSets() {
        return metaDataRestRepository.getSets();
    }

    public List<String> getFormats() {
        return metaDataRestRepository.getFormats();
    }

}

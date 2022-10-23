package magicthebuilder.cardservice.service;

import lombok.AllArgsConstructor;
import magicthebuilder.cardservice.repository.MetaDataRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class MetaDataService {
    private final MetaDataRepository metaDataRestRepository;

    public List<String> getTypes() {
        return metaDataRestRepository.getTypes();
    }

    public List<String> getSets() {
        return metaDataRestRepository.getSets();
    }

    public List<String> getFormats() {
        return metaDataRestRepository.getFormats();
    }

}

package magicthebuilder.cardservice.controller;

import io.magicthegathering.javasdk.resource.MtgSet;
import lombok.AllArgsConstructor;
import magicthebuilder.cardservice.service.MetaDataService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin
@RestController
@AllArgsConstructor
@RequestMapping("api")
public class MetaDataController {
    private final MetaDataService metaDataService;

    @GetMapping("sets")
    public List<MtgSet> getSets() {
        return metaDataService.getSets();
    }

    @GetMapping("types")
    public List<String> getTypes() {
        return metaDataService.getTypes();
    }

    @GetMapping("formats")
    public List<String> getFormats() {
        return metaDataService.getFormats();
    }
}

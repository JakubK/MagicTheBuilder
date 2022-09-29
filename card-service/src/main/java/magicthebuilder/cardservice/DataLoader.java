package magicthebuilder.cardservice;


import io.magicthegathering.javasdk.resource.Card;
import lombok.RequiredArgsConstructor;
import magicthebuilder.cardservice.entity.MtgCard;
import magicthebuilder.cardservice.repository.CardRepository;
import magicthebuilder.cardservice.repository.FileRepository;
import magicthebuilder.cardservice.repository.RestRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@RequiredArgsConstructor
@Component
public class DataLoader implements ApplicationRunner {
    private final CardRepository cardRepository;
    private final RestRepository restRepository;
    private final FileRepository fileRepository;
    @Value("${readFromFile}")
    private Boolean readFromFile;

    public void run(ApplicationArguments args) {
        List<Card> cardsOriginalFormat = readFromFile ? fileRepository.getAllCards() : restRepository.getAll();
        List<MtgCard> cards = cardsOriginalFormat.parallelStream().map(MtgCard::new).toList();
        cardRepository.saveAll(cards);
    }
}

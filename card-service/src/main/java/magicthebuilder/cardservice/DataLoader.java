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
import org.springframework.scheduling.annotation.Scheduled;
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
        if (readFromFile) {
            List<Card> cardsOriginalFormat = fileRepository.getAllCards();
            List<MtgCard> cards = cardsOriginalFormat.parallelStream().map(MtgCard::new).toList();
            cardRepository.saveAll(cards);
            System.out.println("Database filled with cards from file");
        } else {
            updateAllCards();
        }
    }

    @Scheduled(cron = "* */30 * * * *")
    public void updateAllCards() {
        List<MtgCard> newCards = new java.util.ArrayList<>(restRepository.getAll().parallelStream().map(MtgCard::new).toList());
        List<MtgCard> oldCards = cardRepository.findAll();
        newCards.removeAll(oldCards);
        cardRepository.saveAll(newCards);
        System.out.println("Database updated with cards from Gatherer");
    }

    public void downloadImages() {

    }
}

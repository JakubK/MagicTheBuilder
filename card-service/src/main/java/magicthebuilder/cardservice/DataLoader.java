package magicthebuilder.cardservice;


import io.magicthegathering.javasdk.resource.Card;
import lombok.RequiredArgsConstructor;
import magicthebuilder.cardservice.entity.MtgCard;
import magicthebuilder.cardservice.rabbit.CardIdPublisher;
import magicthebuilder.cardservice.rabbit.CardPublisher;
import magicthebuilder.cardservice.repository.CardRepository;
import magicthebuilder.cardservice.repository.FileRepository;
import magicthebuilder.cardservice.repository.RestRepository;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;

@RequiredArgsConstructor
@Component
public class DataLoader {
    private final CardRepository cardRepository;
    private final RestRepository restRepository;
    private final FileRepository fileRepository;

    private final CardPublisher cardPublisher;
    private final CardIdPublisher cardIdPublisher;
    @Value("${readFromFile}")
    private Boolean readFromFile;

    @PostConstruct
    public void fillDatabaseWithCards() {
        if (readFromFile) {
            try {
                List<Card> cardsOriginalFormat = fileRepository.getAllCards();
                List<MtgCard> cards = cardsOriginalFormat.parallelStream().map(MtgCard::new).toList();
                cardRepository.saveAll(cards);
                System.out.println("Database filled with cards from file");
                return;
            } catch (Exception e) {
                System.out.println("Error occurred while trying to read the file");
            }
        }

        updateAllCards();
    }

    @Scheduled(cron = "* */60 * * * *")
    public void updateAllCards() {
        System.out.println("Downloading cards from gatherer");
        List<MtgCard> newCards = new java.util.ArrayList<>(restRepository.getAll().parallelStream().map(MtgCard::new).toList());
        List<MtgCard> oldCards = cardRepository.findAll();
        newCards.removeAll(oldCards);
        cardRepository.saveAll(newCards);
//        List<MtgCard> newCards = cardRepository.findAll().stream().limit(100).toList();
        System.out.println("Sending " + newCards.size() + " to rabbit");
        SendUpdatesToRabbit(newCards);
        System.out.println("Database updated with cards from Gatherer");
        System.out.println("There are " + newCards.size() + " new or updated cards");
    }

    private void SendUpdatesToRabbit(List<MtgCard> newCards) {
        cardPublisher.sendMsg(newCards);
//        cardIdPublisher.sendMsg(newCards.stream().map(MtgCard::getId).toList());
    }
}

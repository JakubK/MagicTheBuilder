package magicthebuilder.validationservice.repository;


import com.google.gson.Gson;
import lombok.Getter;
import lombok.Setter;
import magicthebuilder.cardservice.entity.MtgCard;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Arrays;
import java.util.stream.Collectors;

@Repository
public class CardRepository {

    @Getter
    @Setter
    private java.util.Map<String, MtgCard> cards;


    @PostConstruct
    private void downloadAllCards() throws InterruptedException {
        Gson gson = new Gson();
        java.net.URI uri;
        HttpResponse<String> getResponse;
        try {
            uri = new URI("http://card-service:8080/api/internal/cards/all");
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
        HttpRequest getRequest = HttpRequest.newBuilder()
                .uri(uri)
                .GET()
                .build();
        HttpClient httpClient = HttpClient.newHttpClient();

        MtgCard[] mtgCards = null;
        while (mtgCards == null || mtgCards.length == 0) {
            try {
                getResponse = httpClient.send(getRequest, HttpResponse.BodyHandlers.ofString());
                mtgCards = gson.fromJson(getResponse.body(), MtgCard[].class);
                break;
            } catch (IOException | InterruptedException e) {
                Thread.sleep(10000);
            }
        }
        cards = Arrays.stream(mtgCards).collect(Collectors.toMap(MtgCard::getId, mtgCard -> mtgCard));
        System.out.println(cards.size() + " downloaded from card service");
    }
}

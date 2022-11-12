package magicthebuilder.validationservice.repository;


import com.google.gson.Gson;
import lombok.Getter;
import magicthebuilder.validationservice.entity.MtgCard;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Repository
public class CardRepository {

    @Getter
    private java.util.Map<String, MtgCard> cards;


    @PostConstruct
    private void downloadAllCards() {
        Gson gson = new Gson();
        java.net.URI uri;
        HttpResponse<String> getResponse;
        try {
            uri = new URI("http://localhost:8081/api/internal/cards/all");
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
        HttpRequest getRequest = HttpRequest.newBuilder()
                .uri(uri)
                .GET()
                .build();
        HttpClient httpClient = HttpClient.newHttpClient();
        try {
            getResponse = httpClient.send(getRequest, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
        MtgCard[] mtgCards = gson.fromJson(getResponse.body(), MtgCard[].class);
        cards = Arrays.stream(mtgCards).collect(Collectors.toMap(MtgCard::getId, mtgCard -> mtgCard));
        System.out.println(cards.size());
        System.out.println(cards.keySet().toArray()[0]);
    }
}

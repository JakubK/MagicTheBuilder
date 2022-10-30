package magicthebuilder.deckservice.dto;

import lombok.Getter;

@Getter

public class CardInDeckAmountPutRequest {
    private String cardId;
    private int amount;
}
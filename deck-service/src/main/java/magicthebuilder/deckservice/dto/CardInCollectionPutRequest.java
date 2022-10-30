package magicthebuilder.deckservice.dto;


import lombok.Getter;

@Getter
public class CardInCollectionPutRequest {
    private String cardId;
    private int amount;
}

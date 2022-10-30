package magicthebuilder.deckservice.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CardInDecksUsageDto {
    List<CardInDeckStatsDto> usageInDecks;
    private int totalAmount;
}


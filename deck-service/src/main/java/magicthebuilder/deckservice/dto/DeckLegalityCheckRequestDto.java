package magicthebuilder.deckservice.dto;

import lombok.Getter;
import lombok.Setter;
import magicthebuilder.deckservice.entity.Card;
import magicthebuilder.deckservice.entity.Deck;
import magicthebuilder.deckservice.entity.enums.GameMode;
import magicthebuilder.deckservice.util.ConversionUtils;

import java.util.*;

@Setter
@Getter
public class DeckLegalityCheckRequestDto {
    private GameMode gameMode;
    private List<MultipleCardDto> deck;
    private List<MultipleCardDto> sideBoard;
    private String commander=null;

    public DeckLegalityCheckRequestDto(Deck deck){
        ConversionUtils utils = new ConversionUtils();
        setGameMode(deck.getGameMode());
        setDeck(utils.CardListToMultipleCardsDto(deck.getCards()));
        setSideBoard(utils.CardListToMultipleCardsDto(deck.getSideboard()));
        if(deck.getCommander()!= null){
            setCommander(deck.getCommander().getId());
        }
    }

}

package magicthebuilder.deckservice.exception.customexceptions;

import java.util.UUID;

public class UnrecognizedDeckException  extends RuntimeException{
    public UnrecognizedDeckException(UUID deckId) {
        super("Deck referenced by UUID :  "+deckId+" does not exist.");    }

}
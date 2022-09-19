package magicthebuilder.deckservice.exception.customexceptions;

import java.util.UUID;

public class InaccessibleDeckException extends RuntimeException{
    public InaccessibleDeckException(UUID deckId) {
        super("Deck referenced by UUID : "+deckId+" is set to private.");    }

}

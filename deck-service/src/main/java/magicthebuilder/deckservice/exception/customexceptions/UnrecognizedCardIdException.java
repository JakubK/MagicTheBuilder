package magicthebuilder.deckservice.exception.customexceptions;

public class UnrecognizedCardIdException extends RuntimeException{
    public UnrecognizedCardIdException(String message) {  // TODO MAKE THIS RETURN ID USED
        super(message);
    }

}

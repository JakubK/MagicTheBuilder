package magicthebuilder.deckservice.exception.customexceptions;

public class UnrecognizedCardIdException extends RuntimeException {
    public UnrecognizedCardIdException(String message) {
        super(message);
    }

}

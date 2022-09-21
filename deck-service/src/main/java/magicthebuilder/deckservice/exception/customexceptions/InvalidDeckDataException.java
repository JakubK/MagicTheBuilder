package magicthebuilder.deckservice.exception.customexceptions;

public class InvalidDeckDataException extends RuntimeException {
    public InvalidDeckDataException(String message) {
        super(message);
    }

}
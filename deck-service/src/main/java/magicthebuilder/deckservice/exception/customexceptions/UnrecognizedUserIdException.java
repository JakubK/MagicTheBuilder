package magicthebuilder.deckservice.exception.customexceptions;

public class UnrecognizedUserIdException extends RuntimeException {
    public UnrecognizedUserIdException(Long userId) {
        super("User with id " + userId + " does not exist.");
    }

}
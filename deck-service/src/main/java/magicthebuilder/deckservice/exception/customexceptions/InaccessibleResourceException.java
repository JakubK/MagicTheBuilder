package magicthebuilder.deckservice.exception.customexceptions;

public class InaccessibleResourceException extends RuntimeException {
    public InaccessibleResourceException(Long userId) {
        super("User with ID " + userId + " is not allowed to access this resource");
    }

}

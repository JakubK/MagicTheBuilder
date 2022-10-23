package magicTheBuilder.userservice.exception.customExceptions;

public class InvalidUsernameException extends RuntimeException {
    public InvalidUsernameException() {
        super("User with provided username does not exist");
    }
}

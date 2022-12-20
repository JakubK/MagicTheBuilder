package magicTheBuilder.userservice.exception.customExceptions;

public class InvalidPasswordException extends RuntimeException {
    public InvalidPasswordException() {
        super("Provided password is incorrect for provided username");
    }
}
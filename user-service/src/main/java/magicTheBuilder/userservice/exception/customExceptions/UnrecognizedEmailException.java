package magicTheBuilder.userservice.exception.customExceptions;

public class UnrecognizedEmailException extends RuntimeException {
    public UnrecognizedEmailException() {
        super("User with provided email does not exist");
    }
}

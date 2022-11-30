package magicTheBuilder.userservice.exception.customExceptions;

public class InvalidEmailFormatException extends RuntimeException {
    public InvalidEmailFormatException(String email) {
        super(email + " is not in correct email format");
    }
}
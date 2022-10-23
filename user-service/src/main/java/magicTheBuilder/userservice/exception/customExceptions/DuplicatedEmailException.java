package magicTheBuilder.userservice.exception.customExceptions;

public class DuplicatedEmailException extends RuntimeException {
    public DuplicatedEmailException(String email) {
        super("User with email " + email + " already exists");
    }
}
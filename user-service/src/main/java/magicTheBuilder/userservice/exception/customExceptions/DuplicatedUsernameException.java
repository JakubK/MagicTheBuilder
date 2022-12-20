package magicTheBuilder.userservice.exception.customExceptions;

import java.util.UUID;

public class DuplicatedUsernameException extends RuntimeException {
    public DuplicatedUsernameException(String username) {
        super("User with username " + username + " already exists");
    }
}
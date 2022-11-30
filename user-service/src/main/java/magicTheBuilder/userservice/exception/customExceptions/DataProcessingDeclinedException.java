package magicTheBuilder.userservice.exception.customExceptions;

public class DataProcessingDeclinedException extends RuntimeException {
    public DataProcessingDeclinedException() {
        super("Allowing data processing is required");
    }
}
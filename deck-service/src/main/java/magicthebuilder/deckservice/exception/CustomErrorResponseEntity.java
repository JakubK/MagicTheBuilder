package magicthebuilder.deckservice.exception;

import lombok.Getter;

import java.time.Instant;

@Getter
public class CustomErrorResponseEntity {
    private final String message;
    private final Instant timestamp;

    public CustomErrorResponseEntity(String message, Instant timestamp) {
        this.message = message;
        this.timestamp = timestamp;
    }
}

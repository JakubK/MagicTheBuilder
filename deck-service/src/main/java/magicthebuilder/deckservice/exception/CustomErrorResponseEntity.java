package magicthebuilder.deckservice.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.time.Instant;
import java.time.ZonedDateTime;

@Getter
public class CustomErrorResponseEntity {
    private final String message;
    private final Instant timestamp;

    public CustomErrorResponseEntity(String message,Instant timestamp) {
        this.message = message;
        this.timestamp = timestamp;
    }
}

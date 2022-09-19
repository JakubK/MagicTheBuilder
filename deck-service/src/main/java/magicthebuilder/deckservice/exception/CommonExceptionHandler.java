package magicthebuilder.deckservice.exception;

import magicthebuilder.deckservice.exception.customexceptions.InaccessibleDeckException;
import magicthebuilder.deckservice.exception.customexceptions.UnrecognizedCardIdException;
import magicthebuilder.deckservice.exception.customexceptions.UnrecognizedDeckException;
import magicthebuilder.deckservice.exception.customexceptions.UnrecognizedUserIdException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;

@ControllerAdvice
public class CommonExceptionHandler {
    @ExceptionHandler({
            UnrecognizedCardIdException.class,
            InaccessibleDeckException.class,
            UnrecognizedDeckException.class,
            UnrecognizedUserIdException.class})
    public ResponseEntity<Object> UnrecognizedCardIdExceptionHandler(RuntimeException e) {
        CustomErrorResponseEntity customErrorResponseEntity = new CustomErrorResponseEntity(
                e.getMessage(),
                Instant.now()
        );
        return new ResponseEntity<>(customErrorResponseEntity, HttpStatus.BAD_REQUEST);
    }


}

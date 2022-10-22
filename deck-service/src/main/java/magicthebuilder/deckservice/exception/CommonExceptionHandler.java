package magicthebuilder.deckservice.exception;

import magicthebuilder.deckservice.exception.customexceptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.Instant;

@ControllerAdvice
public class CommonExceptionHandler {
    @ExceptionHandler({
            UnrecognizedCardIdException.class,
            UnrecognizedDeckException.class,
            UnrecognizedUserIdException.class})
    public ResponseEntity<Object> InvalidDataExceptionHandler(RuntimeException e) {
        CustomErrorResponseEntity customErrorResponseEntity = new CustomErrorResponseEntity(
                e.getMessage(),
                Instant.now()
        );
        return new ResponseEntity<>(customErrorResponseEntity, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({
            InaccessibleDeckException.class,
            InaccessibleResourceException.class
    })
    public ResponseEntity<Object> InaccessibleResourceExceptionHandler(RuntimeException e) {
        CustomErrorResponseEntity customErrorResponseEntity = new CustomErrorResponseEntity(
                e.getMessage(),
                Instant.now()
        );
        return new ResponseEntity<>(customErrorResponseEntity, HttpStatus.FORBIDDEN);
    }
}

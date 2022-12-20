package magicTheBuilder.userservice.exception;

import magicTheBuilder.userservice.exception.customExceptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.Instant;

@ControllerAdvice
public class CommonExceptionHandler {
    @ExceptionHandler({
            DuplicatedUsernameException.class,
            DuplicatedEmailException.class,
            UnrecognizedEmailException.class,
            InvalidPasswordException.class,
            InvalidEmailFormatException.class,
            DataProcessingDeclinedException.class
    })
    public ResponseEntity<Object> InvalidDataExceptionHandler(RuntimeException e) {
        CustomErrorResponseEntity customErrorResponseEntity = new CustomErrorResponseEntity(
                e.getMessage(),
                Instant.now()
        );
        return new ResponseEntity<>(customErrorResponseEntity, HttpStatus.BAD_REQUEST);
    }
}

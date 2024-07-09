package gift.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ProductExceptionHandler {
    @ExceptionHandler(value = Exception.class)
    protected ResponseEntity<String> handleException(Exception e) {
        return new ResponseEntity<>(e.getMessage() ,HttpStatus.BAD_REQUEST);
    }
}


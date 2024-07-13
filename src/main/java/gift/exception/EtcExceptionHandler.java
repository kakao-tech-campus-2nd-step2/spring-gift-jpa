package gift.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class EtcExceptionHandler {
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String,String>> handleEtcExceptions(
            Exception exception
    ){
        Map<String,String> errors = new HashMap<>();
        errors.put("errerEtc",exception.getMessage());
        return ResponseEntity.badRequest().body(errors);
    }
}

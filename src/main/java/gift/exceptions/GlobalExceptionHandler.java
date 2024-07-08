package gift.exceptions;

import gift.common.ErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(InvalidNameException.class)
    public ResponseEntity<Map<String, String>> handleInvalidNameException(InvalidNameException cex) {
        Map<String, String> response = new HashMap<>();
        response.put("message", cex.getMessage());

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidUserException.class)
    public ResponseEntity<Map<String, String>> handleInvalidUserException(InvalidUserException cex) {
        Map<String, String> response = new HashMap<>();
        response.put("message", cex.getMessage());

        return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
    }

//    @ExceptionHandler(CustomException.class)
//    public ResponseEntity<Map<String, String>> handleCustomException(CustomException cex) {
//        Map<String, String> response = new HashMap<>();
//        String message = cex.getMessage();
//
//        if (message.equals(ErrorMessage.emailAlreadyExists)) {
//            response.put("message", cex.getMessage());
//            return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
//        }
//
//        if (message.equals(ErrorMessage.passwordInvalid)) {
//            response.put("message", cex.getMessage());
//            return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
//        }
//
//        if (message.equals(ErrorMessage.emailNotExists)) {
//            response.put("message", cex.getMessage());
//            return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
//        }
//    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleInvalidNameExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> error = new HashMap<>();

        String errorMessage = ex.getBindingResult()
                .getAllErrors()
                .get(0)
                .getDefaultMessage();

        error.put("message", errorMessage);
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

}



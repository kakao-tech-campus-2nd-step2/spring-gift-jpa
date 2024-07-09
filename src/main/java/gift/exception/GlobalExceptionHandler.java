package gift.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(InvalidProductException.class)
    public ResponseEntity<String> handleInvalidProductException(InvalidProductException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }
    
    @ExceptionHandler(InvalidUserException.class)
    public ResponseEntity<String> handleInvalidUserException(InvalidUserException e) {
    	return new ResponseEntity<>(e.getMessage(), e.getStatus());
    }
    
    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<String> handleInvalidUserException(UnauthorizedException e){
    	return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
    }
    
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<String> handleInvalidUserException(UserNotFoundException e){
    	return new ResponseEntity<>(e.getMessage(), HttpStatus.FORBIDDEN);
    }
}

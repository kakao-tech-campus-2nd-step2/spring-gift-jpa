package gift.global;

import gift.api.member.EmailAlreadyExistsException;
import gift.api.wishlist.InvalidQuantityException;
import gift.global.exception.ForbiddenMemberException;
import gift.global.exception.UnauthorizedMemberException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> handleValidationException(MethodArgumentNotValidException e) {
        return ResponseEntity.badRequest().body(e.getBindingResult()
                                                .getFieldError()
                                                .getDefaultMessage());
    }

    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<String> handleEmailRedundancyException(EmailAlreadyExistsException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @ExceptionHandler(UnauthorizedMemberException.class)
    public ResponseEntity<String> handleUnauthorizationException(UnauthorizedMemberException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(ForbiddenMemberException.class)
    public ResponseEntity<String> handleForbiddenMemberException(ForbiddenMemberException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(UnsupportedOperationException.class)
    public ResponseEntity<String> handleUnsupportedOperationException(UnsupportedOperationException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @ExceptionHandler(InvalidQuantityException.class)
    public ResponseEntity<String> handleInvalidQuantityException(InvalidQuantityException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}

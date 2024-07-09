package gift.domain.exception;

import gift.global.response.ErrorResponse;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 서버에서 발생하는 예외를 종합적으로 처리하는 클래스
 */
@RestControllerAdvice(basePackages = {"gift.domain", "gift.global"})
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        FieldError error = e.getBindingResult().getFieldError();
        assert error != null;
        return ErrorResponse.of(error.getField() + ": " + error.getDefaultMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleProductNotFoundException(ProductNotFoundException e) {
        return ErrorResponse.notFound(e);
    }

    @ExceptionHandler(ProductAlreadyExistsException.class)
    public ResponseEntity<Map<String, Object>> handleProductAlreadyExistsException(ProductAlreadyExistsException e) {
        return ErrorResponse.conflict(e);
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<Map<String, Object>> handleUUserAlreadyExistsException(UserAlreadyExistsException e) {
        return ErrorResponse.conflict(e);
    }

    @ExceptionHandler(UserIncorrectLoginInfoException.class)
    public ResponseEntity<Map<String, Object>> handleUUserAlreadyExistsException(UserIncorrectLoginInfoException e) {
        return ErrorResponse.of(e, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(UserNotAdminException.class)
    public ResponseEntity<Map<String, Object>> handleExpiredJwtException(UserNotAdminException e) {
        return ErrorResponse.of(e, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleUserNotFoundException(UserNotFoundException e) {
        return ErrorResponse.notFound(e);
    }

    @ExceptionHandler(TokenNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleExpiredJwtException(TokenNotFoundException e) {
        return ErrorResponse.of(e, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(TokenExpiredException.class)
    public ResponseEntity<Map<String, Object>> handleTokenExpiredException(TokenExpiredException e) {
        return ErrorResponse.of(e, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(TokenStringInvalidException.class)
    public ResponseEntity<Map<String, Object>> handleTokenStringInvalidException(TokenStringInvalidException e) {
        return ErrorResponse.of(e, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(TokenUnexpectedErrorException.class)
    public ResponseEntity<Map<String, Object>> handleTokenUnexpectedErrorException(TokenUnexpectedErrorException e) {
        return ErrorResponse.of(e, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(ProductNotIncludedInWishlistException.class)
    public ResponseEntity<Map<String, Object>> handleProductNotIncludedInWishlistException(
        ProductNotIncludedInWishlistException e) {
        return ErrorResponse.notFound(e);
    }
}

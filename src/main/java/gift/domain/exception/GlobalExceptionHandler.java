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
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        FieldError error = e.getBindingResult().getFieldError();
        assert error != null;
        return ErrorResponse.of(error.getField() + ": " + error.getDefaultMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleProductNotFoundException(ProductNotFoundException e) {
        return ErrorResponse.notFound(e);
    }

    @ExceptionHandler(ProductAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleProductAlreadyExistsException(ProductAlreadyExistsException e) {
        return ErrorResponse.conflict(e);
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleUUserAlreadyExistsException(UserAlreadyExistsException e) {
        return ErrorResponse.conflict(e);
    }

    @ExceptionHandler(UserIncorrectLoginInfoException.class)
    public ResponseEntity<ErrorResponse> handleUUserAlreadyExistsException(UserIncorrectLoginInfoException e) {
        return ErrorResponse.of(e, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(UserNotAdminException.class)
    public ResponseEntity<ErrorResponse> handleExpiredJwtException(UserNotAdminException e) {
        return ErrorResponse.of(e, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleUserNotFoundException(UserNotFoundException e) {
        return ErrorResponse.notFound(e);
    }

    @ExceptionHandler(TokenNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleExpiredJwtException(TokenNotFoundException e) {
        return ErrorResponse.of(e, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(TokenExpiredException.class)
    public ResponseEntity<ErrorResponse> handleTokenExpiredException(TokenExpiredException e) {
        return ErrorResponse.of(e, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(TokenStringInvalidException.class)
    public ResponseEntity<ErrorResponse> handleTokenStringInvalidException(TokenStringInvalidException e) {
        return ErrorResponse.of(e, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(TokenUnexpectedErrorException.class)
    public ResponseEntity<ErrorResponse> handleTokenUnexpectedErrorException(TokenUnexpectedErrorException e) {
        return ErrorResponse.of(e, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(ProductNotIncludedInWishlistException.class)
    public ResponseEntity<ErrorResponse> handleProductNotIncludedInWishlistException(
        ProductNotIncludedInWishlistException e) {
        return ErrorResponse.notFound(e);
    }
}

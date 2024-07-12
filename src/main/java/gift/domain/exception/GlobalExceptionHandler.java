package gift.domain.exception;

import gift.global.apiResponse.ErrorApiResponse;
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
    public ResponseEntity<ErrorApiResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        FieldError error = e.getBindingResult().getFieldError();
        assert error != null;
        return ErrorApiResponse.of(error.getField() + ": " + error.getDefaultMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<ErrorApiResponse> handleProductNotFoundException(ProductNotFoundException e) {
        return ErrorApiResponse.notFound(e);
    }

    @ExceptionHandler(ProductAlreadyExistsException.class)
    public ResponseEntity<ErrorApiResponse> handleProductAlreadyExistsException(ProductAlreadyExistsException e) {
        return ErrorApiResponse.conflict(e);
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<ErrorApiResponse> handleUUserAlreadyExistsException(UserAlreadyExistsException e) {
        return ErrorApiResponse.conflict(e);
    }

    @ExceptionHandler(UserIncorrectLoginInfoException.class)
    public ResponseEntity<ErrorApiResponse> handleUUserAlreadyExistsException(UserIncorrectLoginInfoException e) {
        return ErrorApiResponse.of(e, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(UserNotAdminException.class)
    public ResponseEntity<ErrorApiResponse> handleExpiredJwtException(UserNotAdminException e) {
        return ErrorApiResponse.of(e, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorApiResponse> handleUserNotFoundException(UserNotFoundException e) {
        return ErrorApiResponse.notFound(e);
    }

    @ExceptionHandler(TokenNotFoundException.class)
    public ResponseEntity<ErrorApiResponse> handleExpiredJwtException(TokenNotFoundException e) {
        return ErrorApiResponse.of(e, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(TokenExpiredException.class)
    public ResponseEntity<ErrorApiResponse> handleTokenExpiredException(TokenExpiredException e) {
        return ErrorApiResponse.of(e, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(TokenStringInvalidException.class)
    public ResponseEntity<ErrorApiResponse> handleTokenStringInvalidException(TokenStringInvalidException e) {
        return ErrorApiResponse.of(e, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(TokenUnexpectedErrorException.class)
    public ResponseEntity<ErrorApiResponse> handleTokenUnexpectedErrorException(TokenUnexpectedErrorException e) {
        return ErrorApiResponse.of(e, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(ProductNotIncludedInWishlistException.class)
    public ResponseEntity<ErrorApiResponse> handleProductNotIncludedInWishlistException(
        ProductNotIncludedInWishlistException e) {
        return ErrorApiResponse.notFound(e);
    }
}

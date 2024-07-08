package gift.exception;

import gift.exception.product.ProductNotFoundException;
import gift.exception.user.UserAlreadyExistException;
import gift.exception.user.UserNotFoundException;
import gift.exception.user.UserUnauthorizedException;
import gift.exception.wish.WishNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ProblemDetail handleMethodArgumentNotValidException (MethodArgumentNotValidException ex) {
        String message = ex.getBindingResult()
                            .getAllErrors()
                            .getFirst()
                            .getDefaultMessage();

        return ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, message);
    }

    @ExceptionHandler(value = ProductNotFoundException.class)
    public ProblemDetail handleProductNotFoundException (ProductNotFoundException ex) {
        return ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    @ExceptionHandler(value = UserAlreadyExistException.class)
    public ProblemDetail handleUserAlreadyExistException (UserAlreadyExistException ex) {
        return ProblemDetail.forStatusAndDetail(HttpStatus.CONFLICT, ex.getMessage());
    }

    @ExceptionHandler(value = UserNotFoundException.class)
    public ProblemDetail handleUserNotFoundException (UserNotFoundException ex) {
        return ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    @ExceptionHandler(value = UserUnauthorizedException.class)
    public ProblemDetail handleUserUnauthorizedException (UserUnauthorizedException ex) {
        return ProblemDetail.forStatusAndDetail(HttpStatus.UNAUTHORIZED, ex.getMessage());
    }

    @ExceptionHandler(value = WishNotFoundException.class)
    public ProblemDetail handleWishNotFoundException (WishNotFoundException ex) {
        return ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, ex.getMessage());
    }
}

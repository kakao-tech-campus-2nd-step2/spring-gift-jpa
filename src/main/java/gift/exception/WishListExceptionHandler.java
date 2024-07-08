package gift.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class WishListExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(WishListException.class)
    public ErrorResult duplicatedWish(WishListException e) {
        return new ErrorResult("위시 리스트 에러", e.getMessage());
    }
}

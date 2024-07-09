package gift.controller.wishlist;

import gift.exception.WishAlreadyExistsException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice(basePackageClasses = WishListController.class)
public class WishListControllerAdvice {

    @ExceptionHandler(WishAlreadyExistsException.class)
    public ProblemDetail handleWishAlreadyExistsException(WishAlreadyExistsException e) {
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.CONFLICT);
        problemDetail.setTitle(e.getMessage());
        return problemDetail;
    }

}

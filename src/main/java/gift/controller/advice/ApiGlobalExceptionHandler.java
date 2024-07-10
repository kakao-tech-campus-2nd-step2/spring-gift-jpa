package gift.controller.advice;

import gift.exception.EmailDuplicateException;
import gift.exception.MemberNotFoundException;
import gift.exception.ProductNotFoundException;
import gift.exception.WishAlreadyExistsException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice(basePackages = "gift.controller.api")
public class ApiGlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ProblemDetail handleValidationExceptions(MethodArgumentNotValidException e) {
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        problemDetail.setTitle("Validation fail");

        List<String> invalidReasonList = e.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(FieldError::getDefaultMessage)
                .collect(Collectors.toList());
        problemDetail.setProperty("invalidReasons", invalidReasonList);

        return problemDetail;
    }

    @ExceptionHandler(MemberNotFoundException.class)
    public ProblemDetail handleMemberNotFoundException(MemberNotFoundException e) {
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        problemDetail.setTitle(e.getMessage());
        return problemDetail;
    }

    @ExceptionHandler(EmailDuplicateException.class)
    public ProblemDetail handleEmailDuplicateException(EmailDuplicateException e) {
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.CONFLICT);
        problemDetail.setTitle(e.getMessage());
        return problemDetail;
    }

    @ExceptionHandler(ProductNotFoundException.class)
    public ProblemDetail handleProductNotFoundExceptions(ProductNotFoundException e) {
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        problemDetail.setTitle(e.getMessage());
        return problemDetail;
    }

    @ExceptionHandler(WishAlreadyExistsException.class)
    public ProblemDetail handleWishAlreadyExistsException(WishAlreadyExistsException e) {
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.CONFLICT);
        problemDetail.setTitle(e.getMessage());
        return problemDetail;
    }

}

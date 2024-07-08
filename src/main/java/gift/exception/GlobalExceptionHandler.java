package gift.exception;

import java.util.ArrayList;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ProblemDetail> handleValidationExceptions(
        MethodArgumentNotValidException exception) {
        ProblemDetail problemDetail = createProblemDetail(exception);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(problemDetail);
    }

    private ProblemDetail createProblemDetail(MethodArgumentNotValidException exception) {
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        problemDetail.setTitle("Validation Failed");
        problemDetail.setDetail("하나 이상의 Validation 문제가 있습니다.");

        BindingResult bindingResult = exception.getBindingResult();
        List<ErrorDTO> errorDetails = getErrorDTOS(bindingResult);

        problemDetail.setProperty("errors", errorDetails);
        return problemDetail;
    }

    private static List<ErrorDTO> getErrorDTOS(BindingResult bindingResult) {
        List<ObjectError> allErrors = bindingResult.getAllErrors();
        List<ErrorDTO> errorDetails = new ArrayList<>();

        for (ObjectError objectError : allErrors) {
            addErrorDetailIfFieldError(errorDetails, objectError);
        }

        return errorDetails;
    }

    private static void addErrorDetailIfFieldError(List<ErrorDTO> errorDetails,
        ObjectError objectError) {
        if (objectError instanceof FieldError) {
            FieldError fieldError = (FieldError) objectError;
            String fieldName = fieldError.getField();
            String errorMessage = fieldError.getDefaultMessage();
            ErrorDTO errorDTO = new ErrorDTO(fieldName, errorMessage);
            errorDetails.add(errorDTO);
        }
    }

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<ProblemDetail> handleProductNotFoundException(
        ProductNotFoundException ex) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND,
            ex.getMessage());
        problemDetail.setTitle("Product Not Found");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(problemDetail);
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<ProblemDetail> handleUnauthorizedException(UnauthorizedException ex) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.UNAUTHORIZED,
            ex.getMessage());
        problemDetail.setTitle("Unauthorized Access");
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(problemDetail);
    }

    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<ProblemDetail> handleEmailAlreadyExistsException(
        EmailAlreadyExistsException ex) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST,
            ex.getMessage());
        problemDetail.setTitle("Email Already Exists");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(problemDetail);
    }

    @ExceptionHandler(UserAuthException.class)
    public ResponseEntity<ProblemDetail> handleWrongAuthorizedException(
        UserAuthException ex) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.FORBIDDEN,
            ex.getMessage());
        problemDetail.setTitle("Wrong authorized Access");
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(problemDetail);
    }

}

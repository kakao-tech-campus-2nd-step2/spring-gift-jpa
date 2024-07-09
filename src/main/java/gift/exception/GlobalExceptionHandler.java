package gift.exception;

import gift.dto.response.ErrorResponse;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception ex) {
        return buildErrorResponse("An error occurred: " + ex.getMessage(), "500", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorResponse> handleDataIntegrityViolationException(DataIntegrityViolationException ex) {
        return buildErrorResponse("Data integrity violation: " + ex.getMessage(), "400", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        ErrorResponse errorResponse = new ErrorResponse(errors, "400");
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleProductNotFoundException(ProductNotFoundException ex) {
        return buildErrorResponse("No such product: " + ex.getMessage(), "404", HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InvalidProductDataException.class)
    public ResponseEntity<ErrorResponse> handleInvalidProductDataException(InvalidProductDataException ex) {
        return buildErrorResponse("Invalid product data: " + ex.getMessage(), "400", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MemberNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleMemberNotFoundException(MemberNotFoundException ex) {
        return buildErrorResponse("로그인 실패: " + ex.getMessage(), "401", HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<ErrorResponse> handleInvalidCredentialsException(InvalidCredentialsException ex) {
        return buildErrorResponse("로그인 실패: " + ex.getMessage(), "401", HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(DuplicateMemberException.class)  // 409 Conflict
    public ResponseEntity<ErrorResponse> handleDuplicateMemberException(DuplicateMemberException ex) {
        return buildErrorResponse("로그인 실패: " + ex.getMessage(), "409", HttpStatus.CONFLICT);
    }

    @ExceptionHandler(UnAuthorizationException.class)
    public ResponseEntity<ErrorResponse> handleUnAuthorizationException(UnAuthorizationException ex){
        return buildErrorResponse("로그인 실패: " + ex.getMessage(), "401", HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorResponse> handleUnAuthorizationException(AccessDeniedException ex){
        return buildErrorResponse("삭제 실패: " + ex.getMessage(), "403", HttpStatus.FORBIDDEN);
    }

    private ResponseEntity<ErrorResponse> buildErrorResponse(String message, String status, HttpStatus httpStatus) {
        ErrorResponse errorResponse = new ErrorResponse(message, status);
        return new ResponseEntity<>(errorResponse, httpStatus);
    }
}

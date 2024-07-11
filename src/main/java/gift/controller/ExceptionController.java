package gift.controller;

import gift.constant.ErrorMessage;
import gift.exception.GiftException;
import gift.response.ErrorResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionController {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> invalid(MethodArgumentNotValidException e) {
        ErrorResponse response = new ErrorResponse.ErrorResponseBuilder()
                .code(400)
                .message(ErrorMessage.VALIDATION_ERROR.getMessage())
                .build();

        for (FieldError fieldError : e.getFieldErrors()) {
            response.addValidation(fieldError.getField(), fieldError.getDefaultMessage());
        }

        return ResponseEntity.badRequest()
                .body(response);
    }

    @ExceptionHandler(GiftException.class)
    public ResponseEntity<ErrorResponse> giftException(GiftException e) {
        int statusCode = e.getStatusCode();

        ErrorResponse response = new ErrorResponse.ErrorResponseBuilder()
                .code(e.getStatusCode())
                .message(e.getMessage())
                .validation(e.getValidation())
                .build();

        return ResponseEntity.status(statusCode)
                .body(response);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> exception(Exception e) {
        ErrorResponse response = new ErrorResponse.ErrorResponseBuilder()
                .code(500)
                .message(e.getMessage())
                .build();

        return ResponseEntity.status(500)
                .body(response);
    }

}

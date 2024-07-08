package gift.controller;

import gift.dto.common.apiResponse.ApiResponse;
import gift.dto.common.apiResponse.ApiResponseBody.FailureBody;
import gift.dto.common.apiResponse.ApiResponseGenerator;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ApiResponse<FailureBody> handleProductNameLengthExceededException(MethodArgumentNotValidException e){
        FieldError fieldError = e.getBindingResult().getFieldErrors().getFirst();
        return ApiResponseGenerator.fail(HttpStatus.BAD_REQUEST,  fieldError.getField() + " : " + fieldError.getDefaultMessage());
    }
}

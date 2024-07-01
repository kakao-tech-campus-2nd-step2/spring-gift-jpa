package gift.exception;

import gift.dto.ErrorResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorResponseDto> handleElementNotFoundException(NotFoundException e) {
        return createErrorResponseEntity(e.getErrorCode());
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorResponseDto> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e) {
        return createErrorResponseEntity(ErrorCode.INVALID_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDto> handleGenericException(Exception e) {
        return createErrorResponseEntity(ErrorCode.INTERNAL_SERVER_ERROR);
    }


    private ResponseEntity<ErrorResponseDto> createErrorResponseEntity(ErrorCode errorCode) {
        return new ResponseEntity<>(new ErrorResponseDto(errorCode.getMessage()), errorCode.getHttpStatus());
    }
}

package gift.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.HashMap;
import java.util.Map;


record ErrorResponseDto(
    String message,
    Map<String, String> details
) {

}

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorResponseDto> handleElementNotFoundException(NotFoundException e) {
        return createErrorResponseEntity(e.getErrorCode(), Map.of("description", e.getDetails()));
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorResponseDto> handleMethodArgumentTypeMismatchException(
        MethodArgumentTypeMismatchException e) {
        return createErrorResponseEntity(ErrorCode.INVALID_REQUEST, Map.of());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponseDto> handleMethodArgumentNotValidException(
        MethodArgumentNotValidException e) {
        Map<String, String> errors = e.getFieldErrors().stream()
            .collect(
                HashMap::new,
                (map, fieldError) -> map.put(
                    fieldError.getField(),
                    fieldError.getDefaultMessage()
                ),
                HashMap::putAll
            );

        return createErrorResponseEntity(ErrorCode.INVALID_REQUEST, errors);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDto> handleGenericException(Exception e) {
        return createErrorResponseEntity(ErrorCode.INTERNAL_SERVER_ERROR, Map.of());
    }


    private ResponseEntity<ErrorResponseDto> createErrorResponseEntity(ErrorCode errorCode,
        Map<String, String> details) {
        return new ResponseEntity<>(new ErrorResponseDto(errorCode.getMessage(), details),
            errorCode.getHttpStatus());
    }
}

package gift.global.handler;

import gift.global.exception.BusinessException;
import gift.global.response.ErrorResponseDto;
import gift.global.utils.ResponseHelper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErrorResponseDto> handleBusinessException(BusinessException e) {
        return ResponseHelper.createErrorResponse(e.getErrorCode());
    }
}

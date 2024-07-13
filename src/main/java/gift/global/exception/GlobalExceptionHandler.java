package gift.global.exception;

import gift.global.dto.ApiResponseDto;
import java.util.NoSuchElementException;
import org.springframework.core.annotation.Order;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

// 반환 타입을 ApiResponseDto로 통일시키면서 global하게 바꿨습니다.
@RestControllerAdvice
@Order(value = 10)
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ApiResponseDto handler(MethodArgumentNotValidException methodArgumentNotValidException) {
        String message = methodArgumentNotValidException.getFieldError().getDefaultMessage();

        return new ApiResponseDto(message);
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ApiResponseDto handler(NoSuchElementException noSuchElementException) {
        String message = noSuchElementException.getMessage();

        return new ApiResponseDto(message);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ApiResponseDto handler(IllegalArgumentException illegalArgumentException) {
        String message = illegalArgumentException.getMessage();

        return new ApiResponseDto(message);
    }

    @ExceptionHandler(ResponseStatusException.class)
    public ApiResponseDto handler(ResponseStatusException responseStatusException) {
        String message = responseStatusException.getMessage();
        return new ApiResponseDto(message);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ApiResponseDto handler(DataIntegrityViolationException dataIntegrityViolationException) {
        return new ApiResponseDto("제약 조건에 위배됩니다.");
    }

    // 헤더가 유실된 경우인데, 보통은 조작을 빨리 해서 토큰이 누락된 경우 예외가 발생합니다.
    @ExceptionHandler(MissingRequestHeaderException.class)
    public ApiResponseDto handler(MissingRequestHeaderException missingRequestHeaderException) {
        return new ApiResponseDto("조작이 너무 빠릅니다.");
    }
}

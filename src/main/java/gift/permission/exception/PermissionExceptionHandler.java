package gift.permission.exception;

import gift.global.dto.ApiResponseDto;
import java.util.NoSuchElementException;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(basePackages = "gift.permission.controller")
@Order(value = 1)
public class PermissionExceptionHandler {

    // repository의 get() 메서드 사용 시, 조회된 것이 없으면 해당 예외를 반환
    // 비록 검증하는 부분은 하나이지만, service의 verify를 없애서 service의 역할을 분담하고자 하였습니다.
    @ExceptionHandler(NoSuchElementException.class)
    public ApiResponseDto handler() {
        String message = "가입된 유저가 아닙니다.";

        return new ApiResponseDto(message);
    }
}

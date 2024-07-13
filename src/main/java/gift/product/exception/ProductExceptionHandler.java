package gift.product.exception;

import gift.global.dto.ApiResponseDto;
import java.util.NoSuchElementException;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(basePackages = "gift.product.controller")
@Order(value = 1)
public class ProductExceptionHandler {

    // repository의 get() 메서드 사용 시, 조회된 것이 없으면 해당 예외를 반환
    // 불러올 때마다 검증해야 해서 중복을 줄여보고자 만들었습니다.
    @ExceptionHandler(NoSuchElementException.class)
    public ApiResponseDto handler() {
        String message = "존재하지 않는 제품입니다.";

        return new ApiResponseDto(message);
    }
}
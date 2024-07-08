package gift.exceptionHandler;

import gift.constants.ErrorMessage;
import java.util.NoSuchElementException;
import java.util.Objects;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 입력 값 유효성 검증 실패 시, 에러 핸들링
     *
     * @return 400 Bad Request 상태코드와 메시지를 반환
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> inputValidException(MethodArgumentNotValidException e) {
        String errorMsg = Objects.requireNonNull(e.getBindingResult().getFieldError(),
            ErrorMessage.ERROR_FIELD_NOT_EXISTS_MSG).getDefaultMessage();

        return ResponseEntity.badRequest().body(errorMsg);
    }

    /**
     * NoSuchElementException 을 처리
     *
     * @return 400 Bad Request 상태코드와 메시지 반환
     */
    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<String> noSuchElementExceptionHandler(NoSuchElementException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    /**
     * IllegalArgumentException 을 처리
     *
     * @param e
     * @return 400 Bad Request 상태코드와 메시지 반환
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> illegalArgumentExceptionHandler(IllegalArgumentException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}

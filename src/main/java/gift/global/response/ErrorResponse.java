package gift.global.response;

import java.time.LocalDateTime;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

/**
 * 실패 HTTP 응답 생성을 보조하는 유틸리티 클래스
 */
public class ErrorResponse extends BasicResponse {

    private final String message;

    public ErrorResponse(HttpStatusCode statusCode, String message) {
        super(statusCode);
        this.message = message;
    }

    public ErrorResponse(RuntimeException cause, HttpStatusCode statusCode) {
        this(statusCode, cause.getMessage());
    }

    public String getMessage() {
        return message;
    }

    // status 코드에 대한 기본적인 RESTful 응답을 생성
    public static ResponseEntity<ErrorResponse> of(String message, HttpStatusCode statusCode) {
        return ResponseEntity
            .status(statusCode)
            .body(new ErrorResponse(statusCode, message));
    }

    public static ResponseEntity<ErrorResponse> of(RuntimeException cause, HttpStatusCode statusCode) {
        return ErrorResponse.of(cause.getMessage(), statusCode);
    }

    // HTTP code 404에 대한 응답 생성
    public static ResponseEntity<ErrorResponse> notFound(RuntimeException cause) {
        return ErrorResponse.of(cause, HttpStatus.NOT_FOUND);
    }

    // HTTP code 409에 대한 응답 생성
    public static ResponseEntity<ErrorResponse> conflict(RuntimeException cause) {
        return ErrorResponse.of(cause, HttpStatus.CONFLICT);
    }
}

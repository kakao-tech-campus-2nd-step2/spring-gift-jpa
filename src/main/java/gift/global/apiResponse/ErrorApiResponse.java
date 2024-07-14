package gift.global.apiResponse;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

/**
 * 실패 HTTP 응답 생성을 보조하는 유틸리티 클래스
 */
public class ErrorApiResponse extends BasicApiResponse {

    private final String message;

    public ErrorApiResponse(
        @JsonProperty(value = "status", required = true) HttpStatusCode statusCode,
        @JsonProperty(value = "message", required = true) String message
    ) {
        super(statusCode);
        this.message = message;
    }

    public ErrorApiResponse(RuntimeException cause, HttpStatusCode statusCode) {
        this(statusCode, cause.getMessage());
    }

    public String getMessage() {
        return message;
    }

    // status 코드에 대한 기본적인 RESTful 응답을 생성
    public static ResponseEntity<ErrorApiResponse> of(String message, HttpStatusCode statusCode) {
        return ResponseEntity
            .status(statusCode)
            .body(new ErrorApiResponse(statusCode, message));
    }

    public static ResponseEntity<ErrorApiResponse> of(RuntimeException cause, HttpStatusCode statusCode) {
        return ErrorApiResponse.of(cause.getMessage(), statusCode);
    }

    // HTTP code 404에 대한 응답 생성
    public static ResponseEntity<ErrorApiResponse> notFound(RuntimeException cause) {
        return ErrorApiResponse.of(cause, HttpStatus.NOT_FOUND);
    }

    // HTTP code 409에 대한 응답 생성
    public static ResponseEntity<ErrorApiResponse> conflict(RuntimeException cause) {
        return ErrorApiResponse.of(cause, HttpStatus.CONFLICT);
    }
}

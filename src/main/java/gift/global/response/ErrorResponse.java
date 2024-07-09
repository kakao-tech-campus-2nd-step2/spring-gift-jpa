package gift.global.response;

import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

/**
 * 실패 HTTP 응답 생성을 보조하는 유틸리티 클래스
 */
public class ErrorResponse {

    // status 코드에 대한 기본적인 RESTful 응답을 생성
    public static ResponseEntity<Map<String, Object>> of(String message, HttpStatusCode statusCode) {
        return ResponseEntity
            .status(statusCode)
            .body(ResponseBodyBase.get(statusCode, ResponseBodyBase.Entry.of("message", message)));
    }

    public static ResponseEntity<Map<String, Object>> of(RuntimeException cause, HttpStatusCode statusCode) {
        return ErrorResponse.of(cause.getMessage(), statusCode);
    }

    // HTTP code 404에 대한 응답 생성
    public static ResponseEntity<Map<String, Object>> notFound(RuntimeException cause) {
        return ErrorResponse.of(cause, HttpStatus.NOT_FOUND);
    }

    // HTTP code 409에 대한 응답 생성
    public static ResponseEntity<Map<String, Object>> conflict(RuntimeException cause) {
        return ErrorResponse.of(cause, HttpStatus.CONFLICT);
    }
}

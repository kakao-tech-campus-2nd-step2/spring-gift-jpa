package gift.global.apiResponse;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDateTime;
import org.springframework.http.HttpStatusCode;

/**
 * ResponseEntity의 body에 담길 기본 객체(Map<String, Object)를 생성하는 유틸리티 클래스
 */
public class BasicApiResponse {

    private final LocalDateTime timestamp;
    private final Integer status;

    public BasicApiResponse(
        @JsonProperty(value = "timestamp", required = true) LocalDateTime timestamp,
        @JsonProperty(value = "status", required = true) Integer status
    ) {
        this.timestamp = timestamp;
        this.status = status;
    }

    public BasicApiResponse(Integer status) {
        this(LocalDateTime.now(), status);
    }

    public BasicApiResponse(HttpStatusCode statusCode) {
        this(statusCode.value());
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public Integer getStatus() {
        return status;
    }
}

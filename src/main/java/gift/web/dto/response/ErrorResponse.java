package gift.web.dto.response;

import gift.web.validation.exception.code.ErrorCode;
import java.time.LocalDateTime;

public class ErrorResponse {

    private int code;
    private String category;
    private String description;
    private LocalDateTime timestamp;

    public ErrorResponse(int code, String category, String description) {
        this.code = code;
        this.category = category;
        this.description = description;
        this.timestamp = LocalDateTime.now();
    }

    public static ErrorResponse from(ErrorCode errorCode) {
        return new ErrorResponse(errorCode.getCode(), errorCode.getCategory().getDescription(),
            errorCode.getDescription());
    }

    public int getCode() {
        return code;
    }

    public String getCategory() {
        return category;
    }

    public String getDescription() {
        return description;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }
}

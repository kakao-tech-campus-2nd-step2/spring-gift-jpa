package gift.web.dto.response;

import gift.web.validation.exception.CustomException;
import gift.web.validation.exception.code.ErrorCode;
import java.time.LocalDateTime;
import org.springframework.validation.BindingResult;

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

    public static ErrorResponse from(CustomException exception) {
        ErrorCode errorCode = exception.getErrorCode();
        return new ErrorResponse(errorCode.getCode(), errorCode.getCategory().toString(), exception.getMessage());
    }

    public static ErrorResponse from(BindingResult bindingResult) {
        return new ErrorResponse(-40010, "INVALID_PARAMETER", bindingResult.getFieldError().getDefaultMessage());
    }

    public static ErrorResponse of(ErrorCode errorCode, String description) {
        return new ErrorResponse(errorCode.getCode(), errorCode.getCategory().getDescription(), description);
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

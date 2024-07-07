package gift.web.dto.response;

import java.time.LocalDateTime;
import org.springframework.validation.BindingResult;

public class ErrorResponse {

    private String code;
    private String field;
    private String description;
    private LocalDateTime timestamp;

    public ErrorResponse(String code, String field, String description) {
        this.code = code;
        this.description = description;
        this.field = field;
        this.timestamp = LocalDateTime.now();
    }

    public static ErrorResponse from(BindingResult bindingResult) {
        return new ErrorResponse(
            bindingResult.getFieldError().getCode(),
            bindingResult.getFieldError().getField(),
            bindingResult.getFieldError().getDefaultMessage()
        );
    }

    public String getCode() {
        return code;
    }

    public String getField() {
        return field;
    }

    public String getDescription() {
        return description;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }
}

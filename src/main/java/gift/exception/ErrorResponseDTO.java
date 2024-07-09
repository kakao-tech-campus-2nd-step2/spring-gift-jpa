package gift.exception;

import java.util.Map;

public class ErrorResponseDTO {

    private String message;
    private Map<String, String> errors;

    public ErrorResponseDTO(ErrorCode errorCode, Map<String, String> errors) {
        this.message = errorCode.getMessage();
        this.errors = errors;
    }

    public ErrorResponseDTO() {
    }

    public String getMessage() {
        return message;
    }

    public Map<String, String> getErrors() {
        return errors;
    }
}

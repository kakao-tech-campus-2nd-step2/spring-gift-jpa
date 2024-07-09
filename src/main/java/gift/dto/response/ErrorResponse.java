package gift.dto.response;

import java.util.Map;

public class ErrorResponse {
    private String message;
    private String status;
    private Map<String, String> errors;

    public ErrorResponse(String message, String status) {
        this.message = message;
        this.status = status;
    }

    public ErrorResponse(Map<String, String> errors, String status) {
        this.errors = errors;
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public String getStatus() {
        return status;
    }

    public Map<String, String> getErrors() {
        return errors;
    }
}

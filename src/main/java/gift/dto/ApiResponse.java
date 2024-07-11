package gift.dto;

import org.springframework.http.HttpStatusCode;

public class ApiResponse {
    private final HttpStatusCode httpStatusCode;
    private final String message;

    public ApiResponse(HttpStatusCode httpStatusCode, String message) {
        this.httpStatusCode = httpStatusCode;
        this.message = message;
    }

    public HttpStatusCode getHttpStatusCode() {
        return httpStatusCode;
    }

    public String getMessage() {
        return message;
    }
}

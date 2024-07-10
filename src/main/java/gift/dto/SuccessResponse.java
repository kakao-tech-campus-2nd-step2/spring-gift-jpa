package gift.dto;

import org.springframework.http.HttpStatusCode;

public class SuccessResponse {
    private final HttpStatusCode httpStatusCode;
    private final String message;

    public SuccessResponse(HttpStatusCode httpStatusCode, String message) {
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

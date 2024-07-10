package gift.exceptionAdvisor;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

public class MemberServiceException extends RuntimeException {

    private HttpStatus responseStatus;

    public MemberServiceException() {
    }

    public MemberServiceException(String message, HttpStatus responseStatus) {
        super(message);
        this.responseStatus = responseStatus;
    }

    public HttpStatusCode getStatusCode() {
        return responseStatus;
    }
}

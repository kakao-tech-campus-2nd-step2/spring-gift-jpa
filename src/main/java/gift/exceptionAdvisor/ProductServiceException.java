package gift.exceptionAdvisor;

import org.springframework.http.HttpStatus;

public class ProductServiceException extends RuntimeException {

    private HttpStatus responseStatus;

    public ProductServiceException() {
    }

    public ProductServiceException(String message, HttpStatus responseStatus) {
        super(message);
        this.responseStatus = responseStatus;
    }

    public HttpStatus getStatusCode() {
        return responseStatus;
    }
}

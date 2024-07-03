package gift.exception;

import java.util.List;
import org.springframework.validation.ObjectError;

public class ProductException extends RuntimeException {

    List<ObjectError> errors;

    public ProductException() {
        super();
    }

    public ProductException(String message) {
        super(message);
    }

    public ProductException(String message, Throwable cause) {
        super(message, cause);
    }

    public ProductException(Throwable cause) {
        super(cause);
    }

    protected ProductException(String message, Throwable cause, boolean enableSuppression,
        boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public ProductException(List<ObjectError> allErrors) {
        super();
        errors = allErrors;
    }

    public List<ObjectError> getErrors() {
        return errors;
    }
}

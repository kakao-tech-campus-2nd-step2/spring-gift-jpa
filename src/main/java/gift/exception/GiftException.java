package gift.exception;

import java.util.Map;

public abstract class GiftException extends RuntimeException {

    public Map<String, String> validation;

    public GiftException(String message) {
        super(message);
    }

    public GiftException(String message, Map<String, String> validation) {
        super(message);
        this.validation = validation;
    }

    public abstract int getStatusCode();

    public Map<String, String> getValidation() {
        return validation;
    }

}

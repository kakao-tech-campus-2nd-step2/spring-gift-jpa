package gift.web.validation.exception.client;

import gift.web.validation.exception.CustomException;

public abstract class ClientException extends CustomException {

    protected ClientException() {
        super();
    }

    protected ClientException(String message) {
        super(message);
    }

    protected ClientException(String message, Throwable cause) {
        super(message, cause);
    }

    protected ClientException(Throwable cause) {
        super(cause);
    }

}

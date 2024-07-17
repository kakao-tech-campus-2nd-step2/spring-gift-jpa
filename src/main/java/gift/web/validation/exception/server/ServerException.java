package gift.web.validation.exception.server;

import gift.web.validation.exception.CustomException;

public abstract class ServerException extends CustomException {

    protected ServerException() {
        super();
    }

    protected ServerException(String message) {
        super(message);
    }

    protected ServerException(String message, Throwable cause) {
        super(message, cause);
    }

    protected ServerException(Throwable cause) {
        super(cause);
    }

}

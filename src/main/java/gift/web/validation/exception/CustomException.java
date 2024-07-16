package gift.web.validation.exception;

import gift.web.validation.exception.code.ErrorCode;

/**
 * 커스텀 예외를 정의하고자 하는 경우 상속하여야 한다.
 */
public abstract class CustomException extends RuntimeException {

    public CustomException() {
        super();
    }

    public CustomException(String message) {
        super(message);
    }

    public CustomException(String message, Throwable cause) {
        super(message, cause);
    }

    public CustomException(Throwable cause) {
        super(cause);
    }

    /**
     * 예외가 어떤 {@link ErrorCode} 를 가지는지 반환한다.
     * @return {@link ErrorCode}
     */
    public abstract ErrorCode getErrorCode();

}

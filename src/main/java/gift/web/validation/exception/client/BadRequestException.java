package gift.web.validation.exception.client;

import static gift.web.validation.exception.code.ErrorCode.BAD_REQUEST;

import gift.web.validation.exception.code.ErrorCode;

/**
 * 4XX 예외
 */
public class BadRequestException extends ClientException {

    private static final String ERROR_MESSAGE = "잘못된 요청입니다. 요청 URL: %s";

    public BadRequestException() {
        super();
    }

    public BadRequestException(String url) {
        super(ERROR_MESSAGE.formatted(url));
    }

    public BadRequestException(String url, Throwable cause) {
        super(ERROR_MESSAGE.formatted(url), cause);
    }

    public BadRequestException(Throwable cause) {
        super(cause);
    }

    @Override
    public ErrorCode getErrorCode() {
        return BAD_REQUEST;
    }

}

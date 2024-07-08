package gift.common.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.List;

public class ErrorResponse {

    private final String code;
    private final int status;
    private final String message;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<ValidationError> invalidParams;

    public ErrorResponse(ErrorCode errorCode) {
        this.code = errorCode.getCode();
        this.status = errorCode.getHttpStatus().value();
        this.message = errorCode.getMessage();
    }

    public String getCode() {
        return code;
    }

    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public void setInvalidParams(List<ValidationError> invalidParams) {
        this.invalidParams = invalidParams;
    }

    public List<ValidationError> getInvalidParams() {
        return invalidParams;
    }
}

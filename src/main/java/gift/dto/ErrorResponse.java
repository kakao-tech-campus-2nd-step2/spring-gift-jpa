package gift.dto;

import java.util.List;

public class ErrorResponse<T> {
    private T message;

    public ErrorResponse(T message) {
        this.message = message;
    }

    public T getMessage() {
        return message;
    }

    public void setMessage(T message) {
        this.message = message;
    }
}

package gift.product;

import org.springframework.http.HttpStatus;

import java.util.HashMap;

public class ResponseDTO {
    HttpStatus code;
    String message;
    Object data;

    public ResponseDTO() {
    }

    public ResponseDTO(HttpStatus code, String message, Object data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public HttpStatus getCode() {
        return code;
    }

    public void setCode(HttpStatus code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(HashMap<?, ?> data) {
        this.data = data;
    }
}

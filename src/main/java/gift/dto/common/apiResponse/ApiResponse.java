package gift.dto.common.apiResponse;

import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

public class ApiResponse<B> extends ResponseEntity<B> {

    public ApiResponse(B body, HttpStatusCode status) {
        super(body, status);
    }
}

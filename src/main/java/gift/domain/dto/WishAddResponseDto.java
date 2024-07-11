package gift.domain.dto;

import gift.global.response.BasicResponse;
import org.springframework.http.HttpStatusCode;

public class WishAddResponseDto extends BasicResponse {

    private final WishAddResult result;

    public WishAddResponseDto(HttpStatusCode statusCode,
        WishAddResult result) {
        super(statusCode);
        this.result = result;
    }

    public WishAddResult getResult() {
        return result;
    }
}

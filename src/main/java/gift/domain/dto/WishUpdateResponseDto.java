package gift.domain.dto;

import gift.global.response.BasicResponse;
import org.springframework.http.HttpStatusCode;

public class WishUpdateResponseDto extends BasicResponse {

    private final WishResponseDto result;

    public WishUpdateResponseDto(HttpStatusCode statusCode,
        WishResponseDto result) {
        super(statusCode);
        this.result = result;
    }

    public WishResponseDto getResult() {
        return result;
    }
}

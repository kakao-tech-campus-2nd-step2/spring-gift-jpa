package gift.domain.dto;

import gift.global.response.BasicResponse;
import org.springframework.http.HttpStatusCode;

public class UserLoginResponseDto extends BasicResponse {

    private final String token;

    public UserLoginResponseDto(HttpStatusCode statusCode, String token) {
        super(statusCode);
        this.token = token;
    }

    public String getToken() {
        return token;
    }
}

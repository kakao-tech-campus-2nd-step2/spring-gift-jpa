package gift.domain.dto;

import gift.global.response.BasicResponse;
import org.springframework.http.HttpStatusCode;

public class UserRegisterResponseDto extends BasicResponse {

    private final String token;

    public UserRegisterResponseDto(HttpStatusCode statusCode, String token) {
        super(statusCode);
        this.token = token;
    }

    public String getToken() {
        return token;
    }
}

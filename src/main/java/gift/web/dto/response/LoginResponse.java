package gift.web.dto.response;

import gift.authentication.Token;

public class LoginResponse {

    private final Token token;

    public LoginResponse(Token token) {
        this.token = token;
    }

    public Token getToken() {
        return token;
    }
}

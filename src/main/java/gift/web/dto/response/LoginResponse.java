package gift.web.dto.response;

import gift.authentication.token.Token;

public class LoginResponse {

    private Token token;

    private LoginResponse() {
    }

    public LoginResponse(Token token) {
        this.token = token;
    }

    public Token getToken() {
        return token;
    }
}

package gift.dto;

public class AuthResponse {

    private String Token;

    public AuthResponse(String Token) {
        this.Token = Token;
    }

    public String getToken() {
        return Token;
    }

    public void setToken(String Token) {
        this.Token = Token;
    }
}

package gift.dto;

public class UserResponse {

    private String Token;

    public UserResponse(String Token) {
        this.Token = Token;
    }

    public String getToken() {
        return Token;
    }

    public void setToken(String Token) {
        this.Token = Token;
    }
}

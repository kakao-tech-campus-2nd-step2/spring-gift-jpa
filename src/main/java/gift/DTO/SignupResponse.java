package gift.DTO;

public class SignupResponse {

    private String token;

    public SignupResponse(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }
}

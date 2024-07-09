package gift.domain;

public class TokenAuth {
    private final String token;
    private final String email;
    public TokenAuth(String token, String email){
        this.token = token;
        this.email = email;
    }

    public String getToken() {
        return token;
    }

    public String getEmail() {
        return email;
    }
}

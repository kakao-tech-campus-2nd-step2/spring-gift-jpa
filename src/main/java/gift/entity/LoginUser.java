package gift.entity;

public class LoginUser {
    private String email;
    private String type;
    private String token;
    public LoginUser() {}
    public LoginUser(String email, String type, String token) {
        this.email = email;
        this.type = type;
        this.token = token;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}

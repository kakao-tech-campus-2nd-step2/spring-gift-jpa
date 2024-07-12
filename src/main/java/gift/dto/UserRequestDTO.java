package gift.dto;

public class UserRequestDTO {
    private String email;
    private String password;
    private String token;

    public UserRequestDTO(String email, String password, String token) {
        this.email = email;
        this.password = password;
        this.token = token;
    }

    public UserRequestDTO() {
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}

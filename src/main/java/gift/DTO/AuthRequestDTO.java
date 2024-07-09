package gift.DTO;

public class AuthRequestDTO {

    private String email;
    private String password;

    AuthRequestDTO(String email, String password) {
        this.email = email;
        this.password = password;
    }
    AuthRequestDTO() {

    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
package gift.dto;

public class UserRegisterDto {
    private final String email;
    private final String password;

    public UserRegisterDto(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
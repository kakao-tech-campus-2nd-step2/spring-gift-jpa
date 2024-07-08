package gift.dto;

public class UserLoginDto {
    public final String email;
    public final String password;

    public UserLoginDto(String email, String password) {
        this.email = email;
        this.password = password;
    }
}

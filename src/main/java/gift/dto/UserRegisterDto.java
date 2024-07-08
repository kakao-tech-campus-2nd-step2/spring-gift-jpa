package gift.dto;

public class UserRegisterDto {
    public final String email;
    public final String password;

    public UserRegisterDto(String email, String password) {
        this.email = email;
        this.password = password;
    }
}


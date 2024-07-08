package gift.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class LoginInfoRequest {

    @NotBlank(message = "email 값은 공백일 수 없습니다.")
    @Email(message = "올바른 이메일 형식이어야 합니다.")
    String email;

    @NotBlank(message = "password 값은 공백일 수 없습니다.")
    String password;

    public LoginInfoRequest(String email, String password) {
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

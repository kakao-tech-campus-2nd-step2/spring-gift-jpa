package gift.model.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class UserForm {
    @NotBlank
    @Size(min = 3,max = 30,message = "이메일의 길이는 3 이상 ~ 30 이하입니다.")
    private final String email;
    @NotBlank
    @Size(min = 3,max = 15,message = "비밀번호의 길이는 3 이상 ~ 15 이하입니다.")
    private final String password;

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public UserForm(String email, String password) {
        this.email = email;
        this.password = password;
    }
}

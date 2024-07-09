package gift.Model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class Member {
    @NotBlank(message = "입력은 공백일 수 없습니다.")
    @Email(message = "이메일 형식이어야 합니다.")
    private String email;
    @NotBlank(message = "입력은 공백일 수 없습니다.")
    private String password;


    public Member(){
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}

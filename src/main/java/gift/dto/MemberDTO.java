package gift.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public class MemberDTO {

    @Email(message = "유효한 이메일 주소를 입력해주세요.")
    @NotEmpty(message = "이메일은 필수 항목입니다.")
    public String email;

    @Size(min = 6, message = "비밀번호는 최소 6자 이상이어야 합니다.")
    @NotEmpty(message = "비밀번호는 필수 항목입니다.")
    public String password;

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

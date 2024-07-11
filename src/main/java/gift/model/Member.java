package gift.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;

public class Member {
    private Long id;

    @Email(message = "이메일 형식이 올바르지 않습니다.")
    @NotEmpty(message = "이메일은 필수 항목입니다.")
    private String email;

    @NotEmpty(message = "비밀번호는 필수 항목입니다.")
    private String password;

    // getters and setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

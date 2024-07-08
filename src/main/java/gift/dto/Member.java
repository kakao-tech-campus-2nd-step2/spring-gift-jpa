package gift.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class Member {

    Long id;
    @NotBlank(message = "email 값은 공백일 수 없습니다.")
    @Email(message = "올바른 이메일 형식이어야 합니다.")
    String email;
    @NotBlank(message = "password 값은 공백일 수 없습니다.")
    String password;


    public Member(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public Member() {
    }

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

package gift.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import java.util.Base64;

public class Member {

    private Long id;

    @NotBlank(message = "이메일은 1글자 이상 입력하세요")
    @Email(message = "이메일 형식을 지켜야 합니다")
    private final String email;

    @NotBlank(message = "비밀번호는 1글자 이상 입력하세요")
    private final String password;

    public Member(Long id, String email, String password) {
        this.id = id;
        this.email = email;
        this.password = password;
    }

    public static Member createWithEncodedPassword(Long id, String email, String rawPassword) {
        String encodedPassword = Base64.getEncoder().encodeToString(rawPassword.getBytes());
        return new Member(id, email, encodedPassword);
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public void setId(Long id) {
        if (this.id != null) {
            throw new IllegalStateException("ID가 이미 존재합니다");
        }
        this.id = id;
    }

}

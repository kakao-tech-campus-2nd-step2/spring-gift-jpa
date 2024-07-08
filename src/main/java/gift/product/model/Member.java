package gift.product.model;

import jakarta.validation.constraints.NotBlank;

public class Member {

    @NotBlank(message = "이메일은 필수 입력 요소입니다.")
    private String email;

    @NotBlank(message = "비밀번호는 필수 입력 요소입니다.")
    private String password;

    private int role;

    public Member(String email, String password, int role) {
        this.email = email;
        this.password = password;
        this.role = 0;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public int getRole() {
        return role;
    }
}

package gift;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class Member {
    private final Long id;

    @Email(message = "유효한 이메일을 입력해주세요.")
    @NotBlank(message = "이메일은 필수 입력 항목입니다.")
    private final String email;

    @NotBlank(message = "비밀번호는 필수 입력 항목입니다.")
    private final String password;

    public Member(Long id, String email, String password) {
        this.id = id;
        this.email = email;
        this.password = password;
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

    public static class MemberBuilder {
        private Long id;
        private String email;
        private String password;

        public MemberBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public MemberBuilder email(String email) {
            this.email = email;
            return this;
        }

        public MemberBuilder password(String password) {
            this.password = password;
            return this;
        }

        public Member build() {
            return new Member(id, email, password);
        }
    }
}

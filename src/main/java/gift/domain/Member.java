package gift.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "member", uniqueConstraints = {@UniqueConstraint(columnNames = "email")})
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Email(message = "유효한 이메일을 입력해주세요.")
    @NotBlank(message = "이메일은 필수 입력 항목입니다.")
    @Column(nullable = false, unique = true)
    private String email;

    @NotBlank(message = "비밀번호는 필수 입력 항목입니다.")
    @Column(nullable = false)
    private String password;

    protected Member() {
    }

    private Member(MemberBuilder builder) {
        this.id = builder.id;
        this.email = builder.email;
        this.password = builder.password;
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
            return new Member(this);
        }
    }
}

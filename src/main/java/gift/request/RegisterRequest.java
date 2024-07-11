package gift.request;

import gift.domain.member.Member;
import gift.validation.member.NotDuplicateEmail;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.Length;

public class RegisterRequest {

    @NotBlank
    @Length(max = 15)
    private String name;

    @NotBlank
    @Pattern(regexp = "[a-zA-Z0-9_!#$%&’*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$", message = "올바른 이메일 형식을 입력해 주세요.")
    @NotDuplicateEmail
    private String email;

    @NotBlank
    @Length(min = 4, max = 16)
    private String password;

    public Member toEntity() {
        return new Member.MemberBuilder()
                .name(this.name)
                .email(this.email)
                .password(this.password)
                .build();
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

}

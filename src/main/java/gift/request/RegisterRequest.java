package gift.request;

import gift.domain.member.Member;
import gift.constant.ErrorMessage;
import gift.validation.member.NotDuplicateEmail;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;

@Getter
public class RegisterRequest {

    @NotBlank(message = ErrorMessage.MEMBER_NAME_NOT_BLANK)
    @Length(max = 15, message = ErrorMessage.MEMBER_NAME_EXCEEDS_MAX_LENGTH)
    private String name;

    @NotBlank(message = ErrorMessage.EMAIL_NOT_BLANK)
    @Pattern(regexp = "[a-zA-Z0-9_!#$%&’*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$", message = ErrorMessage.INVALID_EMAIL_FORMAT)
    @NotDuplicateEmail
    private String email;

    @NotBlank(message = ErrorMessage.PASSWORD_NOT_BLANK)
    @Length(min = 4, max = 16, message = ErrorMessage.PASSWORD_LENGTH)
    private String password;

    public Member toEntity() {
        return Member.builder()
            .name(this.name)
            .email(this.email)
            .password(this.password)
            .build();
    }

}

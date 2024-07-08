package gift.request;

import gift.constant.ErrorMessage;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;

@Getter
public class LoginRequest {

    @NotBlank(message = ErrorMessage.EMAIL_NOT_BLANK)
    @Pattern(regexp = "[a-zA-Z0-9_!#$%&’*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$", message = ErrorMessage.INVALID_EMAIL_FORMAT)
    private String email;

    @NotBlank(message = ErrorMessage.PASSWORD_NOT_BLANK)
    @Length(min = 4, max = 16, message = ErrorMessage.PASSWORD_LENGTH)
    private String password;

}

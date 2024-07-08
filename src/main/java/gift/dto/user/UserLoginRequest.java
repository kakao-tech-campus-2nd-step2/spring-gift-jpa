package gift.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;

public record UserLoginRequest(
    @Email String email,
    @NotEmpty String password
) {

}

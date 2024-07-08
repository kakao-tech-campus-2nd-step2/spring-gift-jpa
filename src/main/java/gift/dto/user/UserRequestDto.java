package gift.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;

public record UserRequestDto(
    @Email String email,
    @NotEmpty String password
) {

}

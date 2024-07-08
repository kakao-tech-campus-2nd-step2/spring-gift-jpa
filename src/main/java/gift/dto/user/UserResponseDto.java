package gift.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

public record UserResponseDto(
    @NotNull Long id,
    @Email String email,
    String token
) {

}

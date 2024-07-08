package gift.entity;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record User(
    @NotNull Long id,
    @Email String email,
    @NotEmpty String password
) {
}

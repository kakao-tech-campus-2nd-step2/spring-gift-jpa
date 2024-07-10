package gift.model.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record UserRequest(
    @NotBlank
    String password,
    @Email
    String email) {

    public User toEntity() {
        return new User(null, password, email);
    }
}

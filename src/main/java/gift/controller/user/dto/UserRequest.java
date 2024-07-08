package gift.controller.user.dto;

import gift.model.user.Role;
import gift.model.user.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class UserRequest {

    public record Register(
        @Email
        String email,
        @NotBlank
        String password,
        @NotBlank
        String name) {

        public User toEntity() {
            return User.create(null, email(), password(), name(), Role.USER);
        }
    }

    public record Login(
        @Email
        String email,
        @NotBlank
        String password) {

    }
}

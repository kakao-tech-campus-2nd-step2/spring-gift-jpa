package gift.user.application.dto.request;

import static gift.common.validation.ValidateErrorMessage.INVALID_USER_NAME_NULL;
import static gift.common.validation.ValidateErrorMessage.INVALID_USER_NAME_PATTERN;
import static gift.common.validation.ValidateErrorMessage.INVALID_USER_PASSWORD_NULL;

import gift.user.domain.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

public record UserSignUpRequest(
        @NotNull(message = INVALID_USER_NAME_NULL)
        @Email(message = INVALID_USER_NAME_PATTERN)
        String username,
        @NotNull(message = INVALID_USER_PASSWORD_NULL)
        String password
) {
    public User toModel() {
        return new User(username, password);
    }

    public User toModel(Long id) {
        return new User(id, username, password);
    }
}

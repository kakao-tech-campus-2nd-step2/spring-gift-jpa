package gift.member.application.dto.request;

import static gift.common.validation.ValidateErrorMessage.INVALID_USER_NAME_NULL;
import static gift.common.validation.ValidateErrorMessage.INVALID_USER_NAME_PATTERN;
import static gift.common.validation.ValidateErrorMessage.INVALID_USER_PASSWORD_NULL;

import gift.member.service.dto.MemberInfoParam;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

public record MemberSignUpRequest(
        @NotNull(message = INVALID_USER_NAME_NULL)
        @Email(message = INVALID_USER_NAME_PATTERN)
        String username,
        @NotNull(message = INVALID_USER_PASSWORD_NULL)
        String password
) {
    public MemberInfoParam toServiceDto() {
        return new MemberInfoParam(username, password);
    }
}

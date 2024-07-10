package gift.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record MemberRequest(
        @Email(message = "이메일 형식의 입력이어야 합니다.")
        @NotNull
        @NotEmpty
        String email,
        @NotNull
        @NotEmpty
        String password
) {
}

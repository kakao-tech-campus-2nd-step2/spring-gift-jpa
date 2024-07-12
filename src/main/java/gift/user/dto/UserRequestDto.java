package gift.user.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

// input으로 들어오는 user 정보를 담는 DTO
// input으로 들어오므로 유효성 검증은 필수
public record UserRequestDto(
    @NotNull(message = "이메일은 필수로 입력해야 합니다.")
    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$", message = "올바른 이메일 형식이 아닙니다.")
    String email,

    @NotNull(message = "비밀번호는 필수로 입력해야 합니다.")
    @Size(min = 6, message = "비밀번호는 최소 6자 이상이어야 합니다.")
    String password) {

}

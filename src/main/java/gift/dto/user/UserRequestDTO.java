package gift.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;

public record UserRequestDTO(
        @Email(message = "email 형식에 맞지 않습니다.")
        String email,

        @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9]).{8,15}$", message = "비밀번호는 8 ~ 15자로, 대문자, 소문자, 숫자가 반드시 포함되어야 합니다.")
        String password
) {
}

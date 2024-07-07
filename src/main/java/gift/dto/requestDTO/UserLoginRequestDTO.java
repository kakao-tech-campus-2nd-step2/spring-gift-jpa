package gift.dto.requestDTO;

import gift.domain.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;

public record UserLoginRequestDTO(
    @Email(message = "이메일 형식이 아닙니다.")
    String email,
    String password) {

    public static User toEntity(UserLoginRequestDTO userLoginRequestDTO) {
        return new User(userLoginRequestDTO.email(), userLoginRequestDTO.password(), null);
    }
}

package gift.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record MemberDTO(
    @NotBlank
    @Email(message = "이메일 양식에 맞지 않습니다.")
    String email,

    @NotBlank
    String password
) {

}

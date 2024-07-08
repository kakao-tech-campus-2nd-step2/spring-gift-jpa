package gift.dto;

import gift.annotation.NewPasswordsMatch;
import jakarta.validation.constraints.NotBlank;

@NewPasswordsMatch
public record MemberPasswordDTO(
    @NotBlank
    String password,

    @NotBlank
    String newPassword1,

    @NotBlank
    String newPassword2
) {

}

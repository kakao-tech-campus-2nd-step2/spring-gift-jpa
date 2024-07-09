package gift.api.member;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record MemberRequest(
    @NotBlank(message = "Email is mandatory")
    @Email(message = "Must be in email format")
    String email,
    @NotBlank(message = "Password is mandatory")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)\\S{8,}$", message = "Must contain upper and lower case letters, numbers, and no blanks")
    String password,
    @NotNull
    Role role
) {}

package gift.doamin.user.dto;

import gift.doamin.user.entity.UserRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class SignUpForm {

    @NotBlank
    @Email
    private String email;

    @NotBlank
    private String password;


    @Size(min = 1, max = 15)
    @NotBlank
    private String name;

    @NotNull
    private UserRole role;

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    public UserRole getRole() {
        return role;
    }
}

package gift.entity;

import gift.validation.constraint.EmailConstraint;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

public class UserDTO {
    @EmailConstraint
    @Length(min = 1, max = 50)
    @NotNull
    private String email;
    @Length(min = 1, max = 50)
    @NotNull
    private String password;

    public UserDTO() {
    }

    public UserDTO(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

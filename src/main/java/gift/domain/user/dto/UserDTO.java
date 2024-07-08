package gift.domain.user.dto;

import gift.domain.user.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class UserDTO {

    @Email
    private String email;
    @NotBlank
    private String password;

    public UserDTO(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public UserDTO() {
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

    public User toUser() {
        return new User(this.email, this.password); // dto to entity
    }
}

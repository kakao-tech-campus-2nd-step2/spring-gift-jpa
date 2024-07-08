package gift.model.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class UserDTO {

    @NotNull
    private final Long id;
    @NotBlank
    private final String email;
    @NotBlank
    private final String passWord;

    public UserDTO(Long id, String email, String passWord) {
        this.id = id;
        this.passWord = passWord;
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public String getPassWord() {
        return passWord;
    }

    public String getEmail() {
        return email;
    }
}
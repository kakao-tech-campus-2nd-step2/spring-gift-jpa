package gift.domain;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class User {

    @Email
    @NotBlank
    private String email;

    @NotBlank
    private String password;

    public User() {
    }


    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    //getter
    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    public void setPassword(String password) {
        this.password = password;
    }
}

package gift.domain;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

public class UserInfo {
    @NotNull
    private String password;
    @NotNull
    @Email
    private String email;

    public UserInfo(String password, String email) {
        this.password = password;
        this.email = email;
    }

    public String getPassword() {
        return password;
    }


    public String getEmail() {
        return email;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

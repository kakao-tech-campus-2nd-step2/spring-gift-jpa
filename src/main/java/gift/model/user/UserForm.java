package gift.model.user;

import jakarta.validation.constraints.NotNull;

public class UserForm {
    @NotNull
    private final String email;
    @NotNull
    private final String passWord;

    public String getEmail() {
        return email;
    }

    public String getPassWord() {
        return passWord;
    }

    public UserForm(String email, String passWord) {
        this.email = email;
        this.passWord = passWord;
    }
}

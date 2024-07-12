package gift.dto;

import java.beans.ConstructorProperties;

public class UserDTO {

    private String email;
    private String password;

    public UserDTO(){}

    @ConstructorProperties({"email", "password"})
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

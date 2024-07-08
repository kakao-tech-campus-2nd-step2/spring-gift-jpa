package gift.entity;

import java.beans.ConstructorProperties;

public class User {
    private String email;
    private String password;
    private String type; //1 : admin  2 :user

    public User(){

    }

    @ConstructorProperties({"email", "password", "type"})
    public User(String email, String password, String type) {
        this.email = email;
        this.password = password;
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

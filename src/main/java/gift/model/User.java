package gift.model;

import gift.util.EmailConstraint;
import org.hibernate.validator.constraints.Length;

public class User {
    @EmailConstraint
    @Length(min = 1, max = 50)
    private String email;
    @Length(min = 1, max = 50)
    private String password;

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public User() {

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

    @Override
    public String toString() {
        return "User{" +
                "email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}

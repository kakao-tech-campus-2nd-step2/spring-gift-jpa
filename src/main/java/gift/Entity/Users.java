package gift.Entity;

import gift.Model.User;
import jakarta.persistence.*;

@Entity
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long userId;
    private String email;
    private String name;
    private String password;
    private boolean isAdmin;

    protected Users() {
    }

    protected Users(long userId, String email, String name, String password, boolean isAdmin) {
        this.userId = userId;
        this.email = email;
        this.name = name;
        this.password = password;
        this.isAdmin = isAdmin;
    }

    public static Users createUsers(User user) {
        return new Users(user.getId(), user.getEmail(), user.getName(), user.getPassword(), user.isAdmin());
    }

    // Getters and setters
    public long getId() {
        return userId;
    }

    public void setId(Long userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }
}
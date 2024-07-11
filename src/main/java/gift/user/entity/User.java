package gift.user.entity;

// User DB와 mapping될 엔터티
public class User {

    private long userId;
    private String email;
    private String password;

    public User(long userId, String email, String password) {
        this.userId = userId;
        this.email = email;
        this.password = password;
    }

    public long getUserId() {
        return userId;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
package gift.domain;

public class Member {
    private Long user_id;
    private String email;
    private String password;

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public void setUser_id(long userId) {
        this.user_id = userId;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    public Long getId() {
        return user_id;
    }
}

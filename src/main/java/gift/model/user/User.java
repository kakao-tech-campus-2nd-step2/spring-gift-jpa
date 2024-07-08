package gift.model.user;

public class User {

    private Long id;
    private String password;
    private String email;

    public User(Long id, String password, String email) {
        this.id = id;
        this.password = password;
        this.email = email;
    }

    public Long getId() {return id;}

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }
}

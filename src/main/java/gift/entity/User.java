package gift.entity;

public class User {
    private Long id;
    private String email;
    private String password;

    public User(Long id, String email, String password) {
        this.id= id;
        this.email=email;
        this.password= password;
    }

    public Long getId() { return id; }

    public String getEmail() { return email; }

    public String getPassword() { return password; }
}

package gift.domain;

public class User {
    private Long id;
    private String email;
    private String password;
    private String role;

    public User(String email, String password, String role) {
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public Long getId() {
        return id;
    }
    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
    public String getRole() {
        return role;
    }

    public void updateRole(Role role){
        this.role = role.name();
    }
}

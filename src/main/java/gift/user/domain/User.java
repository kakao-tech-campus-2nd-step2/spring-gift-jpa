package gift.user.domain;

public class User {
    private Long id;
    private String username;
    private String password;
    private Role role;

    public User(Long id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.role = Role.USER;
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.role = Role.USER;
    }

    public User(Long id, String username, String password, Role role) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public Role getRole() {
        return role;
    }

    public boolean isNew() {
        return this.id == null;
    }
}

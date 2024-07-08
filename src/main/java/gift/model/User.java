package gift.model;

import gift.PasswordEncoder;
public class User {

    private Long id;

    private String email;

    private String password;

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public boolean matchesPassword(String rawPassword) {
        return PasswordEncoder.encode(rawPassword).equals(this.password);
    }
}
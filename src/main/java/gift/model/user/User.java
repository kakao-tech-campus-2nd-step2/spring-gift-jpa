package gift.model.user;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, unique = true)
    private String email;

    protected User() {
    }

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

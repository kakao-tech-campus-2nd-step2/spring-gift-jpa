package gift.model.user;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;

@Entity(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String password;

    @NotNull
    @Column(unique = true)
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

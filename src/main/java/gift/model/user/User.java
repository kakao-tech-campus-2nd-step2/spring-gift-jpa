package gift.model.user;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String email;
    @Column(nullable = false)
    private String password;

    public User() {
    }

    public User(Long id, String email, String password) {
        this.id = id;
        this.password = password;
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public String getPassWord() {
        return password;
    }

    public String getEmail() {
        return email;
    }
}
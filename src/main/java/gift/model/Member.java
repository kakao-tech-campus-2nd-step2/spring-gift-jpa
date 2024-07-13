package gift.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "member")
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    protected Member() {
    }

    public Member(Long id, String email, String password) {
        this.id = id;
        this.email = email;
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public void update(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public boolean isEmailMatching(String email) {
        return this.email.equals(email);
    }

    public boolean isPasswordMatching(String password) {
        return this.password.equals(password);
    }

    public boolean isIdMatching(Long id) {
        return this.id.equals(id);
    }
}

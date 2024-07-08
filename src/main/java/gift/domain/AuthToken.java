package gift.domain;

import jakarta.persistence.*;

@Entity
public class AuthToken{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String token;

    @Column(unique = true, nullable = false)
    private String email;

    public Long getId() {
        return id;
    }

    public String getToken() {
        return token;
    }

    public String getEmail() {
        return email;
    }

    public AuthToken() {
    }

    public AuthToken(String token, String email) {
        this.token = token;
        this.email = email;
    }
}

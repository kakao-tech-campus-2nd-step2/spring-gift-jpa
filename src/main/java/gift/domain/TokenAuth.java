package gift.domain;

import jakarta.persistence.*;
@Entity
@Table(name = "tokenauth")
public class TokenAuth {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 255)
    private String token;

    @Column(nullable = false, length = 255)
    private String email;

    public TokenAuth(String token, String email){
        this.token = token;
        this.email = email;
    }

    public TokenAuth() {

    }

}

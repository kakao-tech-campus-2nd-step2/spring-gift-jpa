package gift.entity;

import jakarta.persistence.*;

@Entity
@Table(name ="member")
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "token", nullable = false)
    private String token;

    public Member() {}

    public Member(String email, String password, String token) {
        this.email = email;
        this.password = password;
        this.token = token;
    }

    public Member(int id, String email, String password, String token) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.token = token;
    }

    public String getToken() { return this.token; }
}

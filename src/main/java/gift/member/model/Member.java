package gift.member.model;

import jakarta.persistence.*;

@Entity
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    public String email;
    public String password;

    public Member() {}

    public Member(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public Long id() {
        return id;
    }

    @Column(nullable = false, unique = true)
    public String email() {
        return email;
    }

    @Column(nullable = false)
    public String password() {
        return password;
    }

    public void password(String password) {
        this.password = password;
    }

    public void email(String email) {

    }
}
package gift.domain.member;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

@Entity
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(unique = true)
    @NotNull
    private String email;

    @NotNull
    private String name;

    @NotNull
    private String password;
    private int role;

    public Member(String email, String name, String password, int role) {
        this.email = email;
        this.name = name;
        this.password = password;
        this.role = role;
    }

    protected Member() {
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public boolean isMatch(String password) {
        return password.equals(this.password);
    }
}

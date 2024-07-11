package gift.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.util.List;

@Entity
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    @NotNull
    private String email;

    @NotNull
    private String password;

    @OneToMany(mappedBy = "member")
    private List<Wish> wishes;

    public Member(String email, String password) {
        this.email = email;
        this.password = password;
    }

    protected Member() {
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

}

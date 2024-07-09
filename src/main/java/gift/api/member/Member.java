package gift.api.member;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;

@Entity
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    @Column(unique = true)
    private String email;
    @NotNull
    private String password;
    @Enumerated(EnumType.STRING)
    private Role role;

    protected Member() {
    }

    public Member(MemberRequest memberRequest) {
        this.email = memberRequest.email();
        this.password = memberRequest.password();
        this.role = memberRequest.role();
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String toString() {
        return "Member{" +
            "id=" + id +
            ", email='" + email + '\'' +
            ", password='" + password + '\'' +
            ", role=" + role +
            '}';
    }
}

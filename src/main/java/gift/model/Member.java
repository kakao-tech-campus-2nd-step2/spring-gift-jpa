package gift.model;

import gift.common.enums.Role;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class Member extends BasicEntity {

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    protected Member() {}

    public Member(String email, String password, Role role) {
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public Member(Long id, LocalDateTime createdAt, LocalDateTime updatedAt, String email, String password, Role role) {
        super(id, createdAt, updatedAt);
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public void updateMember(String email, String password, Role role) {
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public Role getRole() {
        return role;
    }
}

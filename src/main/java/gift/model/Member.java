package gift.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import java.util.List;

@Entity
@Table(name = "member")
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "role")
    private String role;

    @OneToMany(mappedBy = "member", cascade = CascadeType.REMOVE)
    private List<Wishlist> wishlistList;

    protected Member() {
    }

    public Member(Long id, String name, String email, String password, String role) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getRole() {
        return role;
    }

    public void validate() {
        validateNullName();
        validateEmptyName();
        validateNullEmail();
        validateEmptyEmail();
        validateInvalidEmail();
        validateNullPassword();
        validateEmptyPassword();
    }

    private void validateNullName() {
        if (name == null) {
            throw new IllegalArgumentException("이름을 입력하세요.");
        }
    }

    private void validateEmptyName() {
        if (name.trim().isEmpty()) {
            throw new IllegalArgumentException("이름을 입력하세요.");
        }
    }

    private void validateNullEmail() {
        if (email == null) {
            throw new IllegalArgumentException("이메일을 입력하세요.");
        }
    }

    private void validateEmptyEmail() {
        if (email.trim().isEmpty()) {
            throw new IllegalArgumentException("이메일을 입력하세요.");
        }
    }

    private void validateInvalidEmail() {
        if (!email.matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
            throw new IllegalArgumentException("유효한 이메일을 입력하세요.");
        }
    }

    private void validateNullPassword() {
        if (password == null) {
            throw new IllegalArgumentException("비밀 번호를 입력하세요.");
        }
    }

    private void validateEmptyPassword() {
        if (password.trim().isEmpty()) {
            throw new IllegalArgumentException("비밀 번호를 입력하세요.");
        }
    }

}

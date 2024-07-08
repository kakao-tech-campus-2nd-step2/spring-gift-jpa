package gift.entity;

import gift.exception.BusinessException;
import gift.exception.ErrorCode;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "users", uniqueConstraints = {@UniqueConstraint(columnNames = "email")})
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Email(message = "유효한 이메일 주소여야 합니다.")
    @NotBlank(message = "이메일은 빈 칸일 수 없습니다.")
    @Column(nullable = false, unique = true)
    private String email;

    @NotBlank(message = "비밀번호는 빈 칸일 수 없습니다.")
    @Column(nullable = false)
    private String password;

    protected User() {
    }

    public User(Long id, String email, String password) {
        validateEmail(email);
        validatePassword(password);
        this.id = id;
        this.email = email;
        this.password = password;
    }

    public User(String email, String password) {
        this(null, email, password);
    }

    public void update(String email, String password) {
        validateEmail(email);
        validatePassword(password);
        this.email = email;
        this.password = password;
    }

    private void validateEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            throw new BusinessException(ErrorCode.INVALID_EMAIL);
        }
    }

    private void validatePassword(String password) {
        if (password == null || password.trim().isEmpty()) {
            throw new BusinessException(ErrorCode.INVALID_PASSWORD);
        }
    }

    public boolean isPasswordCorrect(String password) {
        return this.password.equals(password);
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}

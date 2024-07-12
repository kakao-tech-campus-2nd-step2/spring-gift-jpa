package gift.entity;

import gift.exception.BusinessException;
import gift.exception.ErrorCode;

import jakarta.persistence.*;

@Entity
@Table(name = "`user`", uniqueConstraints = {@UniqueConstraint(columnNames = "email")})
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, columnDefinition = "VARCHAR(255) NOT NULL COMMENT 'User Email'")
    private String email;

    @Column(nullable = false, columnDefinition = "VARCHAR(255) NOT NULL COMMENT 'User Password'")
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
        if (!email.contains("@")) {
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

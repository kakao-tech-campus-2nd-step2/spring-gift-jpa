package gift.Model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import java.beans.ConstructorProperties;

@Entity
@Table(name = "member")
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "입력은 공백일 수 없습니다.")
    @Email(message = "이메일 형식이어야 합니다.")
    @Column(name = "email", nullable = false, unique = true)
    private String email;
    @NotBlank(message = "입력은 공백일 수 없습니다.")
    @Column(name = "password", nullable = false)
    private String password;

    protected Member(){

    }

    @ConstructorProperties({"id","email","password"})
    public Member(Long id, String email, String password) {
        this.id = id;
        this.email = email;
        this.password = password;
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

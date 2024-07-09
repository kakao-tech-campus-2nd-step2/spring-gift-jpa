package gift.DTO;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@Table
public class UserDto {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotBlank(message = "Email을 필수입니다.")
  @Email(message = "Email형식으로 제출해주십오.")
  @NotBlank
  @Column(nullable = false, unique = true)
  private String email;

  @NotBlank(message = "비밀번호는 필수입니다.")
  @Size(min = 5, max = 15)
  @Column(nullable = false)
  private String password;

  public UserDto() {

  }

  public UserDto(Long id, String email, String password) {
    this.id = id;
    this.email = email;
    this.password = password;
  }


  public String getEmail() {
    return this.email;
  }

  public String getPassword() {
    return this.password;
  }

}

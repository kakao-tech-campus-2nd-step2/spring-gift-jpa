package gift.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class UserDto {

  @NotBlank(message = "Email을 필수입니다.")
  @Email(message = "Email형식으로 제출해주십오.")
  @NotBlank
  private final String email;

  @NotBlank(message = "비밀번호는 필수입니다.")
  @Size(min = 5, max = 15)
  private final String password;

  public UserDto(String email, String password) {
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

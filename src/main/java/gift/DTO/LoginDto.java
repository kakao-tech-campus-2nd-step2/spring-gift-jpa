package gift.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public class LoginDto {

  @NotEmpty(message = "Email을 필수입니다.")
  @Email(message = "Email형식으로 제출해주십오.")
  @NotBlank
  private String email;

  @NotBlank
  @Size(min=5,max=15)
  private String pw;

  public LoginDto(String email, String pw) {
    this.email = email;
    this.pw = pw;
  }


  public String getEmail() {
    return this.email;
  }


  public String getPw() {
    return this.pw;
  }

}

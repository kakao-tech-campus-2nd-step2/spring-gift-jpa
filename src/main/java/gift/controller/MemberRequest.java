package gift.controller;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;

public class MemberRequest {

  @Email(message = "유효하지 않은 이메일 형식입니다.")
  @NotEmpty(message = "이메일을 입력해야 합니다.")
  private String email;

  @NotEmpty(message = "비밀번호를 입력해야 합니다.")
  private String password;

  public MemberRequest(String email, String password) {
    this.email = email;
    this.password = password;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }
}

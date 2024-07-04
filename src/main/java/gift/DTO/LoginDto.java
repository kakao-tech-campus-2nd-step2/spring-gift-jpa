package gift.DTO;

public class LoginDto {

  private String email;
  private String pw;

  public LoginDto(String email, String pw) {
    this.email = email;
    this.pw = pw;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getEmail() {
    return this.email;
  }

  public void setPw(String pw) {
    this.pw = pw;
  }

  public String getPw() {
    return this.pw;
  }

}

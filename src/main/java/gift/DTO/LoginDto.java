package gift.DTO;

public class LoginDto {

  private String username;
  private String pw;
  private String accessToken;

  public LoginDto(String username, String pw, String accessToken) {
    this.username = username;
    this.pw = pw;
    this.accessToken=accessToken;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getUsername() {
    return this.username;
  }

  public void setPw(String pw) {
    this.pw = pw;
  }

  public String getPw() {
    return this.pw;
  }

  public void setAccessToken(String accessToken){this.accessToken=accessToken;}

  public String getAccessToken(){return this.accessToken;}
}

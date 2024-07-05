package gift.DTO;


public class JwtToken {

  private String accessToken;
  private String refreshToken;

  public JwtToken(String accessToken) {
    this.accessToken = accessToken;
  }

  public void setAccessToken(String accessToken) {
    this.accessToken = accessToken;
  }

  public String getAccessToken() {
    return this.accessToken;
  }

  public void setRefreshToken(String refreshToken) {
    this.refreshToken = refreshToken;
  }

  public String getRefreshToken() {
    return this.refreshToken;
  }
}
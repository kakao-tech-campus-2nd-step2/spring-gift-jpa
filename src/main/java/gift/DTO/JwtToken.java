package gift.DTO;


public class JwtToken {

  private String accessToken;
  private String refreshToken;
  private String tokenType;

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

  public void setTokenType(String tokenType) {
    this.tokenType = tokenType;
  }

  public String getTokenType() {
    return this.tokenType;
  }
}
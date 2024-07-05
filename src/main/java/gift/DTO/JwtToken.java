package gift.DTO;


public class JwtToken {

  private String accessToken;
  private String refreshToken;

  public JwtToken(String accessToken) {
    this.accessToken = accessToken;
  }

  public String getAccessToken() {
    return this.accessToken;
  }

}
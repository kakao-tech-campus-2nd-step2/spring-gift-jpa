package gift.global.authentication.jwt;

public record JwtToken(
    String accessToken,
    String refreshToken
) {

}

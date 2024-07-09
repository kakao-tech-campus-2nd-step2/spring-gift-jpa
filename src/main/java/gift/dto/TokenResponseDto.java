package gift.dto;

public class TokenResponseDto {
    private final String token;

    public TokenResponseDto(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }
}

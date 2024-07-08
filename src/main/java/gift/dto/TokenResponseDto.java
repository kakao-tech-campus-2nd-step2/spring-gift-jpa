package gift.dto;

public class TokenResponseDto {
    private String token;

    public TokenResponseDto() {
        this.token = "";
    }

    public TokenResponseDto(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }
}

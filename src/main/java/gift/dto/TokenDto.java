package gift.dto;

import gift.entity.Token;

public class TokenDto {

    String tokenValue;

    public TokenDto(String tokenValue) {
        this.tokenValue = tokenValue;
    }

    public String getTokenValue() {
        return tokenValue;
    }

    public static TokenDto fromEntity(Token token) {
        return new TokenDto(token.getTokenValue());
    }
}

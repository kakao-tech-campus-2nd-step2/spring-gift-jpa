package gift.entity;

public class Token {
    private Long id;
    private String tokenValue;

    public Token(String tokenValue) {
        this.tokenValue = tokenValue;
    }

    public Token(Long newId, String tokenValue) {
        this.id = newId;
        this.tokenValue= tokenValue;
    }

    public Long getId() {
        return id;
    }

    public String getTokenValue() {
        return tokenValue;
    }
}

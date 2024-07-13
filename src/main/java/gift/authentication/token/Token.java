package gift.authentication.token;

public class Token {

    private String value;

    private Token() {
    }

    private Token(String value) {
        this.value = value;
    }

    public static Token from(String value) {
        return new Token(value);
    }

    public String getValue() {
        return value;
    }
}

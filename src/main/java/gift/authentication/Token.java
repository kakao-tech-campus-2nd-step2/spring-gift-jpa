package gift.authentication;

public class Token {

    private String value;

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

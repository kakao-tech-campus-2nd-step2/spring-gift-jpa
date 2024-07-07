package gift.domain.vo;

public class Email {

    private final String value;

    private Email(String value) {
        this.value = value;
    }

    public static Email from(String email) {
        return new Email(email);
    }

    public String getValue() {
        return value;
    }
}

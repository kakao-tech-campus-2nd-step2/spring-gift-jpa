package gift.domain.vo;

public class Password {

    private final String value;

    private Password(String value) {
        this.value = value;
    }

    public static Password from(String password) {
        return new Password(password);
    }

    public boolean matches(String password) {
        return value.equals(password);
    }

    public String getValue() {
        return value;
    }

}

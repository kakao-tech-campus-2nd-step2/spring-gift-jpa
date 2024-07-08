package gift.member.domain;

import com.fasterxml.jackson.annotation.JsonValue;

public class Email {
    private String value;

    public Email() {}
    public Email(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return value;
    }

    // JSON 직렬화를 위해 @JsonValue 사용
    @JsonValue
    public String toJson() {
        return value;
    }
}

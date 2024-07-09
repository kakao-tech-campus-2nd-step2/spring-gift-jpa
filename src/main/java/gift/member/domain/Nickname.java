package gift.member.domain;

import com.fasterxml.jackson.annotation.JsonValue;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class Nickname {
    @Column(name = "nickname")
    private String value;

    public Nickname() {}
    public Nickname(String value) {
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

package gift.dto;

public class Token {
    Long memberId;
    String value;

    public Token(Long memberId, String value) {
        this.memberId = memberId;
        this.value = value;
    }

    public Token() {
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

}

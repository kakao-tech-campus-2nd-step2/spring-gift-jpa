package gift.exception;

public class MemberException extends RuntimeException {

    private MemberErrorCode memberErrorCode;
    private String detailMessage;

    public MemberException(MemberErrorCode memberErrorCode) {
        super(memberErrorCode.getMessage());
        this.memberErrorCode = memberErrorCode;
        this.detailMessage = memberErrorCode.getMessage();
    }

    public MemberErrorCode getMemberErrorCode() {
        return memberErrorCode;
    }
}

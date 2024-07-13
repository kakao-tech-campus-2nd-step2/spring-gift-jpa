package gift.exception;

public class MemberException extends RuntimeException {

    private final MemberErrorCode memberErrorCode;
    private final String detailMessage;

    public MemberException(MemberErrorCode memberErrorCode) {
        super(memberErrorCode.getMessage());
        this.memberErrorCode = memberErrorCode;
        this.detailMessage = memberErrorCode.getMessage();
    }

    public MemberErrorCode getMemberErrorCode() {
        return memberErrorCode;
    }
}

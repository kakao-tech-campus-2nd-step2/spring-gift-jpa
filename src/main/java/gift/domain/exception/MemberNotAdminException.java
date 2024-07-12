package gift.domain.exception;

public class MemberNotAdminException extends RuntimeException {

    public MemberNotAdminException() {
        super("어드민 권한이 없습니다.");
    }
}

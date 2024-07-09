package gift.domain.exception;

public class UserNotAdminException extends RuntimeException{

    public UserNotAdminException() {
        super("어드민 권한이 없습니다.");
    }
}

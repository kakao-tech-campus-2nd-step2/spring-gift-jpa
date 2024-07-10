package gift.member.error;

import gift.error.CustomException;
import gift.error.ErrorCode;

public class MemberAlreadyExistsException extends CustomException {

    public MemberAlreadyExistsException() {
        super(ErrorCode.MEMBER_ALREADY_EXISTS);
    }

}

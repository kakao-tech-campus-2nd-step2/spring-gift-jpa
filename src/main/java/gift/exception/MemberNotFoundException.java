package gift.exception;

import gift.constant.ErrorMessage;

public class MemberNotFoundException extends GiftException {

    public MemberNotFoundException() {
        super(ErrorMessage.MEMBER_NOT_FOUND.getMessage());
    }

    @Override
    public int getStatusCode() {
        return 404;
    }

}

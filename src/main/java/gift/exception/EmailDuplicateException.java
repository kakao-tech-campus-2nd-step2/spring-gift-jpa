package gift.exception;

import gift.entity.Member;

public class EmailDuplicateException extends RuntimeException {

    public EmailDuplicateException(Member member) {
        super(member.getEmail() + " already in use");
    }

}

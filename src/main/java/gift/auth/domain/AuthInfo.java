package gift.auth.domain;

import gift.member.domain.Email;
import gift.member.domain.Member;
import gift.member.domain.NickName;

public record AuthInfo(Email email, NickName nickName) {
    public AuthInfo(Member member) {
        this(member.getEmail(), member.getNickName());
    }
}

package gift.member.dto;

import gift.member.domain.Email;
import gift.member.domain.Member;
import gift.member.domain.NickName;
import gift.member.domain.Password;

public record MemberServiceDto(Long id, Email email, Password password, NickName nickName) {
    public Member toMember() {
        return new Member(id, email, password, nickName);
    }
}

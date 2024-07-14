package gift.domain.dto.request;

import gift.domain.entity.Member;
import gift.global.util.HashUtil;

public record MemberRequest(String email, String password) {

    public static MemberRequest of(Member member) {
        return new MemberRequest(member.getEmail(), member.getPassword());
    }

    public Member toEntity(String permission) {
        return new Member(email, HashUtil.hashCode(password), permission);
    }
}

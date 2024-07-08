package gift.member.application;

import gift.member.domain.Member;

public record MemberResponse(
        String email,
        String password
) {
    public static MemberResponse from(Member member) {
        return new MemberResponse(member.getEmail(), member.getPassword());
    }
}

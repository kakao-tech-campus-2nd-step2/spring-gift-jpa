package gift.controller.member;

import gift.domain.Member;

public record MemberDto(String email, String password) {
    public static MemberDto of(Member member) {
        return new MemberDto(member.email(), member.password());
    }
    public static Member toMember(MemberDto member) {
        return new Member(member.email(), member.password());
    }
}

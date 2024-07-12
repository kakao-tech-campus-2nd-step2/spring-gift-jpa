package gift.controller.member;

import gift.domain.Member;

public record MemberDto(Long id, String email, String password) {
    public static MemberDto of(Member member) {
        return new MemberDto(member.getId(), member.getEmail(), member.getPassword());
    }
}

package gift.member.dto;

import gift.member.entity.Member;

public record MemberResDto(
        Long id,
        String email
) {

    public MemberResDto(Member member) {
        this(member.getId(), member.getEmail());
    }
}

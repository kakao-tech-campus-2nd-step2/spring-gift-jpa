package gift.member.dto;

import gift.member.Member;

public record MemberResDto(
        Long id,
        String name,
        String email,
        String role
) {

    public MemberResDto(Member member) {
        this(member.getId(), member.getName(), member.getEmail(), member.getRole().getValue());
    }
}

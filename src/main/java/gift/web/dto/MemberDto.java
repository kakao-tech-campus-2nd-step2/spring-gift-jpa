package gift.web.dto;

import gift.domain.member.Member;

public record MemberDto(String email,
                        String password) {
    public static MemberDto from(Member member) {
        return new MemberDto(member.email(), member.password());
    }

    public static Member toEntity(MemberDto memberDto) {
        return new Member(null, memberDto.email(), memberDto.password(), null);
    }
}

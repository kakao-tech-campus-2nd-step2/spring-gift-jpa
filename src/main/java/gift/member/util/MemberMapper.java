package gift.member.util;

import gift.member.domain.Member;
import gift.member.dto.MemberDto;

public class MemberMapper {

    public static Member toEntity(MemberDto memberDto) {
        return new Member(
                null,
                memberDto.email(),
                memberDto.password()
        );
    }

}

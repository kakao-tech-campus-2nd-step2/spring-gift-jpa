package gift.member.dto;

import gift.member.entity.Member;
import gift.member.Role;

public record MemberReqDto(
        String name,
        String email,
        String password
) {

    public Member toEntity(Role role) {

        return new Member(
                null,
                name,
                email,
                password,
                role
        );
    }
}

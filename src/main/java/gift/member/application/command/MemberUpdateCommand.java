package gift.member.application.command;

import gift.member.domain.Member;

public record MemberUpdateCommand(
        Long id,
        String email,
        String password
) {
    public Member toMember() {
        return new Member(id, email, password);
    }
}

package gift.member.presentation.request;

import gift.member.application.command.MemberUpdateCommand;

public record MemberUpdateRequest(
        String email,
        String password
) {
    public MemberUpdateCommand toCommand(Long id) {
        return new MemberUpdateCommand(
                id,
                email,
                password
        );
    }
}

package gift.member.presentation.request;

import gift.member.application.command.MemberUpdateCommand;

public record MemberUpdateRequest(
        String password
) {
    public MemberUpdateCommand toCommand(String email) {
        return new MemberUpdateCommand(
                email,
                password
        );
    }
}

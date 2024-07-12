package gift.member.presentation.request;

import gift.member.application.command.MemberPasswordUpdateCommand;

public record MemberPasswordUpdateRequest(
        String password
) {
    public MemberPasswordUpdateCommand toCommand(Long id) {
        return new MemberPasswordUpdateCommand(
                id,
                password
        );
    }
}

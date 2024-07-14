package gift.member.presentation.request;

import gift.member.application.command.MemberEmailUpdateCommand;

public record MemberEmailUpdateRequest(
        String email
        ) {
    public MemberEmailUpdateCommand toCommand(Long id) {
        return new MemberEmailUpdateCommand(
                id,
                email
        );
    }
}

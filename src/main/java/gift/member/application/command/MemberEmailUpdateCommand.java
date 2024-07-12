package gift.member.application.command;

public record MemberEmailUpdateCommand(
        Long id,
        String email
) {
}

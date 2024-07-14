package gift.member.application.command;

public record MemberPasswordUpdateCommand(
        Long id,
        String password
) {
}

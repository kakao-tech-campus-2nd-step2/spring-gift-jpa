package gift.core.domain.user;

public record UserAccount(
        String principal,
        String credentials
) {
}
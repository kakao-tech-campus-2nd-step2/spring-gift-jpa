package gift.core.domain.user;

public record User(
        Long id,
        String name,
        UserAccount account
) {
}

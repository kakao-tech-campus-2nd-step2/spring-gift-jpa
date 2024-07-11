package gift.user.service.dto;

public record UserSignupInfo(
        Long id,
        String token
) {
    public static UserSignupInfo of(final Long id, final String token) {
        return new UserSignupInfo(id, token);
    }
}
package gift.user.service.dto;

public record UserSignupInfos(
        Long id,
        String token
) {
    public static UserSignupInfos of(final Long id, final String token) {
        return new UserSignupInfos(id, token);
    }
}
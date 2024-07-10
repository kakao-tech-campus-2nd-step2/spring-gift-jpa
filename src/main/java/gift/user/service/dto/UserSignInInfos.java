package gift.user.service.dto;

public record UserSignInInfos(
        String token
) {
    public static UserSignInInfos of(final String token) {
        return new UserSignInInfos(token);
    }
}

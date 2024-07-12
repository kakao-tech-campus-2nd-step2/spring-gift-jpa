package gift.user.service.dto;

public record UserSignInInfo(
        String token
) {
    public static UserSignInInfo of(final String token) {
        return new UserSignInInfo(token);
    }
}

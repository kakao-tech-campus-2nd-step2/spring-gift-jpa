package gift.dto.user;

public record UserResponse(
    Long id,
    String email,
    String token
) {

}

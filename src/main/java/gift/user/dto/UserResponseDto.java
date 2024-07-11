package gift.user.dto;

// DAO로부터 돌려받는 dto.
public record UserResponseDto(long userId, String email, String password) {

}

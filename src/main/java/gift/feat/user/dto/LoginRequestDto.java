package gift.feat.user.dto;

public record LoginRequestDto(
	Long id,
	String email,
	String password,
	String role
) {
}

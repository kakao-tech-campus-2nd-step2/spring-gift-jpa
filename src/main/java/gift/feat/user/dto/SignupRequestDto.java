package gift.feat.user.dto;

import gift.feat.user.User;

public record SignupRequestDto(
	String email,
	String password,
	String role
) {
	// toEntity() 메소드를 추가
	public User toEntity() {
		return User.of(email, password, role);
	}
}

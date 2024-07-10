package gift.feat.product.dto;

import java.util.Collections;
import java.util.List;

import gift.feat.user.User;

public record UserResponseDto(
	// id,email,password,roll,wishList
	Long id,
	String email,
	String password,
	String role
) {
	static public UserResponseDto from(User user) {
		return new UserResponseDto(
			user.getId(),
			user.getEmail(),
			user.getPassword(),
			user.getRole()
		);
	}
}

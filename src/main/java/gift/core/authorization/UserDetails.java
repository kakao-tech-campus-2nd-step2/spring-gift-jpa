package gift.core.authorization;

import java.util.Set;

import io.jsonwebtoken.Claims;
import lombok.Getter;

@Getter
public class UserDetails {
	private Set<Role> roles;
	private Long userId;

	private UserDetails(Set<Role> roles, Long userId) {
		this.roles = roles;
		this.userId = userId;
	}

	public static UserDetails of(Set<Role> roles, Long userId) {
		return new UserDetails(roles, userId);
	}

	// TODO: 권한 부여가 완성 된 후, UserDetailService를 구현하여 UserDetails를 생성할 수 있도록 한다.
	public static UserDetails from(Claims claims) {
		return new UserDetails(
			Set.of(Role.valueOf(claims.get("role", String.class))),
			Long.parseLong(claims.getSubject())
		);
	}
}

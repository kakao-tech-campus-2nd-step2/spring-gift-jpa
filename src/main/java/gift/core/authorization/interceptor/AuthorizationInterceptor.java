package gift.core.authorization.interceptor;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import gift.core.authorization.UserDetails;
import gift.core.jwt.JwtProvider;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
@Order(1)
public class AuthorizationInterceptor implements HandlerInterceptor {
	private final JwtProvider jwtProvider;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
		String rawToken = request.getHeader("Authorization");
		request.setAttribute("USER", UserDetails.from(jwtProvider.getClaims(rawToken)));

		// TODO: 이후 인터셉터에서 Controller의 필요한 권한과 유저의 권한을 비교하는 로직을 구현한다.
		return true;
	}
}
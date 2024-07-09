package gift.global.Interceptor;

import gift.global.exception.BusinessException;
import gift.global.jwt.JwtProvider;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class JwtInterceptor implements HandlerInterceptor {

    private final JwtProvider jwtProvider;

    public JwtInterceptor(JwtProvider jwtProvider) {
        this.jwtProvider = jwtProvider;
    }
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
        Object handler) throws Exception {

        if (request.getHeader("Authorization") == null) {
            throw new BusinessException(HttpStatus.UNAUTHORIZED, "로그인이 필요합니다.");
        }

        String token = request.getHeader("Authorization");

        // TODO 토큰 유효성 검사 필요
        jwtProvider.validateToken(token);
        request.setAttribute("id", jwtProvider.getId(token));
        request.setAttribute("email", jwtProvider.getEmail(token));

        return true;
    }
}

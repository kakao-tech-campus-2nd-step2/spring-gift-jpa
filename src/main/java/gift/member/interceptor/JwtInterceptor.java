package gift.member.interceptor;

import gift.member.error.UnauthorizedException;
import gift.member.util.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class JwtInterceptor implements HandlerInterceptor {

    private final JwtUtil jwtUtil;

    @Value("${jwt.header}")
    private String header;

    @Value("${jwt.prefix}")
    private String prefix;

    public JwtInterceptor(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String authHeader = request.getHeader(header);
        if (authHeader != null && authHeader.startsWith(prefix + " ")) {
            String token = authHeader.substring(prefix.length() + 1);
            if (jwtUtil.isTokenValid(token)) {
                return true;
            }
        }
        throw new UnauthorizedException("Invalid token");
    }
}

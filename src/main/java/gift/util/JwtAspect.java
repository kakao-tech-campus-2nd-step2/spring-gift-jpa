package gift.util;

import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class JwtAspect {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private HttpServletRequest request;

    @Before("@annotation(gift.util.JwtAuthenticated)")
    public void authenticate() {
        String token = request.getHeader("Authorization");
        if (token == null) {
            throw new RuntimeException("JWT Token is missing");
        }

        String email = jwtUtil.extractUsername(token);

        if (!jwtUtil.validateToken(token, email)) {
            throw new RuntimeException("JWT Token is not valid");
        }
    }
}

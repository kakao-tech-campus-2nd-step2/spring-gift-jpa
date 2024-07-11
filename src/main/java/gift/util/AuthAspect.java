package gift.util;


import gift.domain.member.MemberResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.server.ResponseStatusException;

@Aspect
@Component
public class AuthAspect {
    private final JwtUtil jwtUtil;

    public AuthAspect(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Before("@annotation(AuthenticatedUser)")
    public void getUserFromToken() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        String authorizationHeader = request.getHeader("Authorization");

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String token = authorizationHeader.substring(7);
            MemberResponse memberResponse = jwtUtil.getMemberFromToken(token);
            request.setAttribute("authenticatedMember", memberResponse);
        } else {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "로그인이 필요한 기능입니다");
        }
    }
}

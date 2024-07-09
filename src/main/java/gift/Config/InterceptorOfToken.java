package gift.Config;

import gift.Service.TokenService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class InterceptorOfToken implements HandlerInterceptor {

    private final TokenService tokenService;

    public InterceptorOfToken(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
                             Object handler) throws Exception {
        final String authorizationHeader = request.getHeader("Authorization");

        if (isInvalidAuthorizationHeader(authorizationHeader)) {
            setUnauthorizedResponse(response);
            return false;
        }

        String token = extractToken(authorizationHeader);
        String email = tokenService.getEmailFromToken(token);

        if (isInvalidToken(token, email)) {
            setUnauthorizedResponse(response);
            return false;
        }

        setRequestAttributes(request, token);
        return true;
    }

    private boolean isInvalidAuthorizationHeader(String authorizationHeader) {
        return authorizationHeader == null || !authorizationHeader.startsWith("Bearer ");
    }

    private String extractToken(String authorizationHeader) {
        return authorizationHeader.substring(7);
    }

    private boolean isInvalidToken(String token, String email) {
        return email == null || !tokenService.validateToken(token, email);
    }

    private void setUnauthorizedResponse(HttpServletResponse response) {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    }

    private void setRequestAttributes(HttpServletRequest request, String token) {
        request.setAttribute("token", token);
    }
}

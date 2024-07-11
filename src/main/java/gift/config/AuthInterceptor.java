package gift.config;

import gift.model.user.User;
import gift.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Optional;

@Component
public class AuthInterceptor implements HandlerInterceptor {
    private final UserService userService;

    @Autowired
    public AuthInterceptor(UserService userService) {
        this.userService = userService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            Optional<User> user = getUserFromToken(authHeader);
            User resolvedUser = user.orElse(null);
            if (resolvedUser != null) {
                request.setAttribute("user", resolvedUser);
                return true;
            }
        }
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        return false;
    }

    public Optional<User> getUserFromToken(String authHeader) {
        String token = authHeader.replace("Bearer ", "").trim();
        if (userService.validateToken(token)) {
            return userService.getUserByToken(token);
        }
        return Optional.empty();
    }
}

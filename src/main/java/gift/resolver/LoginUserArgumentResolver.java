package gift.resolver;

import gift.model.User;
import gift.service.UserService;
import gift.util.JwtUtil;
import gift.annotation.LoginUser;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Component
public class LoginUserArgumentResolver implements HandlerMethodArgumentResolver {

    private static final String HEADER_NAME = "Authorization";
    private static final String AUTHORIZATION_NAME = "Bearer ";

    private final UserService userService;
    private final JwtUtil jwtUtil;

    public LoginUserArgumentResolver(UserService userService, JwtUtil jwtUtil) {
        this.userService = userService;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterAnnotation(LoginUser.class) != null
            && parameter.getParameterType().equals(User.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
        NativeWebRequest webRequest,
        org.springframework.web.bind.support.WebDataBinderFactory binderFactory) throws Exception {
        String authorizationHeader = webRequest.getHeader(HEADER_NAME);

        if (isTokenPresent(authorizationHeader)) {
            String token = extractToken(authorizationHeader);
            if (jwtUtil.validateToken(token)) {
                return getUserFromToken(token);
            }
        }
        return null;
    }

    private boolean isTokenPresent(String authorizationHeader) {
        return authorizationHeader != null && authorizationHeader.startsWith(AUTHORIZATION_NAME);
    }

    private String extractToken(String authorizationHeader) {
        return authorizationHeader.substring(7);
    }

    private User getUserFromToken(String token) {
        String email = jwtUtil.getSubject(token);
        return userService.findUserByEmail(email);
    }
}
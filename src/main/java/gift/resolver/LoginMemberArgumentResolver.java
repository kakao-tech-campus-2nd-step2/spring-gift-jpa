package gift.resolver;

import gift.entity.User;
import gift.service.TokenService;
import gift.service.UserService;
import gift.util.AuthorizationHeaderProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import jakarta.servlet.http.HttpServletRequest;
import java.util.Optional;

@Component
public class LoginMemberArgumentResolver implements HandlerMethodArgumentResolver {

    private final UserService userService;
    private final TokenService tokenService;
    private final AuthorizationHeaderProcessor authorizationHeaderProcessor;

    @Autowired
    public LoginMemberArgumentResolver(UserService userService, TokenService tokenService, AuthorizationHeaderProcessor authorizationHeaderProcessor) {
        this.userService = userService;
        this.tokenService = tokenService;
        this.authorizationHeaderProcessor = authorizationHeaderProcessor;
    }

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterAnnotation(gift.annotation.LoginMember.class) != null;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, org.springframework.web.bind.support.WebDataBinderFactory binderFactory) throws Exception {
        HttpServletRequest request = webRequest.getNativeRequest(HttpServletRequest.class);
        String token = authorizationHeaderProcessor.extractToken(request);
        String email = tokenService.extractEmail(token);
        Optional<User> user = userService.findUserByEmail(email);

        if (user.isEmpty()) {
            return null;
        }

        return user.get();
    }
}

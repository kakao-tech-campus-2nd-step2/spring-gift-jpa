package gift.resolver;

import gift.annotation.LoginUser;
import gift.service.UserService;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

public class LoginUserArgumentResolver implements HandlerMethodArgumentResolver {
    private final UserService userService;

    public LoginUserArgumentResolver(UserService userService) {
        this.userService = userService;
    }


    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterAnnotation(LoginUser.class) != null;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
        NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
        String token = getToken(webRequest.getHeader("Authorization"));

        return userService.getUserIdByToken(token);
    }

    private String getToken(String authorizationHeader) {
        if(authorizationHeader != null && authorizationHeader.startsWith("Basic ")) {
            return authorizationHeader.substring(6);
        }
        return null;
    }
}

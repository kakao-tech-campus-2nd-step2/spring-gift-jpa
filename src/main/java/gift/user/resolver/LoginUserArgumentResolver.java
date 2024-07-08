package gift.user.resolver;

import gift.user.exception.ForbiddenException;
import gift.user.jwt.JwtService;
import gift.user.model.dto.User;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

public class LoginUserArgumentResolver implements HandlerMethodArgumentResolver {
    private final JwtService jwtService;

    public LoginUserArgumentResolver(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterAnnotation(LoginUser.class) != null &&
                User.class.isAssignableFrom(parameter.getParameterType());
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
        String authorizationHeader = webRequest.getHeader("Authorization");
        if (authorizationHeader != null) {
            User user = jwtService.getLoginUser(authorizationHeader);
            if (user != null) {
                return user;
            }
            throw new ForbiddenException("활성화되지 않은 계정이거나 존재하지 않는 사용자입니다.");
        }
        throw new SecurityException();
    }
}

package gift.resolver;

import gift.annotation.LoginUser;
import gift.domain.User;
import gift.dto.UserResponseDto;
import gift.exception.UserNotFoundException;
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
        return parameter.hasParameterAnnotation(LoginUser.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        String email = (String)webRequest.getAttribute("userEmail",NativeWebRequest.SCOPE_REQUEST);
        System.out.println(email);
        UserResponseDto userResponseDto = userService.findByEmail(email);
        return userResponseDto;
    }
}

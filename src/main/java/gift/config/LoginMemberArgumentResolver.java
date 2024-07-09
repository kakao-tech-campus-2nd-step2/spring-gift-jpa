package gift.config;

import gift.dto.UserRequestDTO;
import gift.model.User;
import gift.service.UserService;
import org.springframework.core.MethodParameter;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.stereotype.Component;

@Component
public class LoginMemberArgumentResolver implements HandlerMethodArgumentResolver {
    private final UserService userService;

    public LoginMemberArgumentResolver(UserService userService) {
        this.userService = userService;
    }

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterAnnotation(LoginMember.class) != null;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
        String token = webRequest.getHeader("Authorization");
        if (token == null || !token.startsWith("Bearer ")) {
            throw new IllegalArgumentException("Authorization 헤더가 존재하지 않거나 유효하지 않습니다.");
        }
        token = token.substring(7);
        UserRequestDTO userRequestDTO = new UserRequestDTO();
        userRequestDTO.setToken(token);
        return userService.findByToken(userRequestDTO);
    }
}

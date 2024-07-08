package gift.controller;

import gift.annotation.LoginMember;
import gift.exception.BadRequestExceptions.UserNotFoundException;
import gift.service.UserService;
import gift.util.JwtUtil;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

public class LoginMemberArgumentResolver implements HandlerMethodArgumentResolver {

    private final UserService userService;
    private final JwtUtil jwtUtil;

    public LoginMemberArgumentResolver(UserService userService, JwtUtil jwtUtil) {
        this.userService = userService;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterAnnotation(LoginMember.class) != null;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
            NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        String token = webRequest.getHeader("Authorization").substring(7);
        String email = jwtUtil.extractEmail(token);
        try {
            return userService.getUser(email);
        } catch (UserNotFoundException e) {
            throw new UserNotFoundException("회원이 아닙니다.");
        }
    }
}
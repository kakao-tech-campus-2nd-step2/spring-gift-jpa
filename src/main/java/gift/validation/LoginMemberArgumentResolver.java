package gift.validation;

import gift.domain.user.User;
import gift.service.user.UserService;
import gift.util.JwtTokenUtil;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import jakarta.servlet.http.HttpServletRequest;

import static gift.util.JwtTokenUtil.extractTokenFromHeader;

@Component
public class LoginMemberArgumentResolver implements HandlerMethodArgumentResolver {

    private final UserService userService;
    private final JwtTokenUtil jwtTokenUtil;

    @Autowired
    public LoginMemberArgumentResolver(UserService userService, JwtTokenUtil jwtTokenUtil) {
        this.userService = userService;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(LoginMember.class) && parameter.getParameterType().equals(User.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        HttpServletRequest servletRequest = (HttpServletRequest) webRequest.getNativeRequest();

        String token = JwtTokenUtil.extractTokenFromHeader(servletRequest);

        if (token != null && !token.isEmpty() && jwtTokenUtil.validateToken(token)) {
            String email = jwtTokenUtil.getClaimsFromToken(token).getSubject();
            User user = userService.findByEmail(email).orElse(null);
            System.out.println("hi");
            return user;
        }

        return null;
    }

}

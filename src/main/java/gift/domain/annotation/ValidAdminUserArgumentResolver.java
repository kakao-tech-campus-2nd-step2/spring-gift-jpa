package gift.domain.annotation;

import gift.domain.entity.User;
import gift.domain.exception.UserNotAdminException;
import gift.domain.service.UserService;
import gift.global.util.JwtUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Component
public class ValidAdminUserArgumentResolver implements HandlerMethodArgumentResolver {

    private final Logger log = LoggerFactory.getLogger(getClass());
    private final UserService userService;
    private final JwtUtil jwtUtil;

    @Autowired
    public ValidAdminUserArgumentResolver(UserService userService, JwtUtil jwtUtil) {
        this.userService = userService;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(ValidAdminUser.class) &&
            User.class.isAssignableFrom(parameter.getParameterType());
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
        NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {

        String authorizationHeader = webRequest.getHeader("Authorization");
        log.info("Header/Authorization: {}", authorizationHeader);
        jwtUtil.checkPrefixOrThrow("Bearer", authorizationHeader);
        String token = jwtUtil.extractTokenFrom(authorizationHeader);

        String userEmail = jwtUtil.getSubject(token);
        User user = userService.findByEmail(userEmail);
        if (!user.permission().equals("admin")) {
            throw new UserNotAdminException();
        }
        return user;
    }
}

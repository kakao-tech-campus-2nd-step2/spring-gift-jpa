package gift.annotation;

import gift.Model.User;
import gift.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Component
public class ValidUserArgumnetResolver implements HandlerMethodArgumentResolver {

    private final UserService userService;

    @Autowired
    public ValidUserArgumnetResolver (UserService userService){
        this.userService = userService;
    }

    @Override
    public boolean supportsParameter(MethodParameter methodParameter){
        return methodParameter.getParameterType().equals(User.class) &&
                methodParameter.hasParameterAnnotation(ValidUser.class);
    }

    @Override
    public Object resolveArgument(MethodParameter methodParameter, ModelAndViewContainer modelAndViewContainer, NativeWebRequest nativeWebRequest, WebDataBinderFactory webDataBinderFactory) throws Exception {
        String token = nativeWebRequest.getHeader("Authorization");
        return userService.getUserByToken(token);
    }
}

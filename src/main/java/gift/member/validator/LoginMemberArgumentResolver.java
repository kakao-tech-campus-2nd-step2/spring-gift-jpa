package gift.member.validator;

import gift.auth.security.JwtFilter;
import gift.auth.error.AuthenticationFailedException;
import gift.auth.error.AuthenticationInvalidException;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Component
public class LoginMemberArgumentResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(LoginMember.class);
    }

    @Override
    public Long resolveArgument(MethodParameter parameter,
                                ModelAndViewContainer mavContainer,
                                NativeWebRequest webRequest,
                                WebDataBinderFactory binderFactory) throws Exception {
        try {
            Long memberId = (Long) webRequest.getAttribute(JwtFilter.REQUEST_ATTRIBUTE_NAME, NativeWebRequest.SCOPE_REQUEST);
            if (memberId == null) {
                throw new AuthenticationInvalidException();
            }
            return memberId;
        } catch (ClassCastException exception) {
            throw new AuthenticationFailedException();
        }
    }

}

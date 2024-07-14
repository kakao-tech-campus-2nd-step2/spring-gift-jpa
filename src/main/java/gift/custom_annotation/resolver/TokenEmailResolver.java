package gift.custom_annotation.resolver;

import gift.custom_annotation.annotation.TokenEmail;
import gift.security.jwt.TokenExtractor;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

public class TokenEmailResolver implements HandlerMethodArgumentResolver {
    private final TokenExtractor tokenExtractor;

    public TokenEmailResolver(TokenExtractor tokenExtractor) {
        this.tokenExtractor = tokenExtractor;
    }

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(TokenEmail.class);
    }

    @Override
    public String resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        String token = webRequest.getHeader("Authorization").substring(7);

        return tokenExtractor.extractEmail(token);
    }
}

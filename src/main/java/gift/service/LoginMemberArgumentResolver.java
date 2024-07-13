package gift.service;


import gift.authorization.JwtUtil;
import gift.dto.TokenLoginRequestDTO;
import jdk.jfr.Description;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;


public class LoginMemberArgumentResolver implements HandlerMethodArgumentResolver {
    private final WishService wishService;
    private final JwtUtil jwtUtil;
    public LoginMemberArgumentResolver(WishService wishService , JwtUtil jwtUtil) {
        this.wishService = wishService;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(gift.service.LoginMember.class);
    }

    @Description("token 추출 후 tokenLoginRequestDTO 객체 반환. 만약 토큰이 유효하지 않다면 null 반환")
    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        String token = webRequest.getHeader("Authorization").substring("Bearer ".length());
        System.out.println("resolving the token . .. ");
        if(jwtUtil.checkClaim(token)){
            String email = jwtUtil.getUserEmail(token);
            TokenLoginRequestDTO tokenLoginRequestDTO = new TokenLoginRequestDTO(email, token);
            return tokenLoginRequestDTO;
        }
        return null;
    }
}

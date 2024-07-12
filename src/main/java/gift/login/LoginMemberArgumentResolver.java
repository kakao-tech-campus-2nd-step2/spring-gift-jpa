package gift.login;

import static gift.login.JwtUtil.verifyToken;

import gift.controller.auth.Token;
import gift.exception.UnauthenticatedException;
import gift.service.MemberService;
import io.jsonwebtoken.Claims;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

public class LoginMemberArgumentResolver implements HandlerMethodArgumentResolver {

    private final MemberService memberService;

    public LoginMemberArgumentResolver(MemberService memberService) {
        this.memberService = memberService;
    }

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(LoginMember.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
        NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        String authHeader = webRequest.getHeader("Authorization");
        if (authHeader == null) {
            throw new UnauthenticatedException("Authorization Header is empty");
        }
        if (!authHeader.startsWith("Bearer ")) {
            throw new UnauthenticatedException("Authorization Header does not start with \'Bearer \'");
        }
        Token token = new Token(authHeader.substring(7));
        Claims claims;
        try {
            claims = verifyToken(token);
        } catch (Exception e) {
            throw new UnauthenticatedException("UnauthenticatedException occured while verifying the token");
        }
        String email = claims.getSubject();
        return memberService.findByEmail(email);
    }
}
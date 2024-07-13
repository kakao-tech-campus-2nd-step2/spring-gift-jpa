package gift.component;

import gift.service.MemberService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import jakarta.servlet.http.HttpServletRequest;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;

@Component
public class LoginMemberArgumentResolver implements HandlerMethodArgumentResolver {

    private final MemberService memberService;
    private final String secretKey;

    public LoginMemberArgumentResolver(MemberService memberService, @Value("${jwt.secret}") String secretKey) {
        this.memberService = memberService;
        this.secretKey = secretKey;
    }

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterAnnotation(LoginMember.class) != null;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
        HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();
        String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String token = authorizationHeader.substring(7);
            Claims claims = Jwts.parser()
                .verifyWith(Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8)))
                .build()
                .parseSignedClaims(token)
                .getPayload();
            Long memberId = Long.parseLong(claims.getSubject());
            return memberService.findById(memberId).orElseThrow(() -> new RuntimeException("Member not found"));
        }
        throw new RuntimeException("Invalid token");
    }
}

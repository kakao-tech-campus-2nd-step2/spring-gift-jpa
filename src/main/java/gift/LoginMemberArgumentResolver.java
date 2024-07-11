package gift;

import java.util.Optional;
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
public class LoginMemberArgumentResolver implements HandlerMethodArgumentResolver {

    private static final Logger logger = LoggerFactory.getLogger(LoginMemberArgumentResolver.class);

    private final MemberService memberService;
    private final JwtUtil jwtUtil;

    @Autowired
    public LoginMemberArgumentResolver(MemberService memberService, JwtUtil jwtUtil) {
        this.memberService = memberService;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(LoginMember.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        String authorizationHeader = webRequest.getHeader("Authorization");
        if (authorizationHeader == null) {
            logger.warn("Authorization header is missing");
            return null;
        }
        if (!authorizationHeader.startsWith("Bearer ")) {
            logger.warn("Authorization header is invalid");
            return null;
        }

        String token = authorizationHeader.substring(7);
        logger.debug("JWT Token: " + token);
        String email = jwtUtil.extractEmail(token);
        if (email == null) {
            logger.warn("Email extracted from token is null");
            return null;
        }

        logger.debug("Extracted Email: " + email);
        Optional<Member> memberOptional = memberService.getMemberByEmail(email);
        if (memberOptional.isPresent()) {
            Member member = memberOptional.get();
            logger.debug("Resolved Member: " + member);
            return member;
        } else {
            logger.warn("No member found for email: " + email);
            return null;
        }
    }
}

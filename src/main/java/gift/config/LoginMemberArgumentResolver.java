package gift.config;

import gift.jwt.JwtUtil;
import gift.model.member.LoginMember;
import gift.model.member.Member;
import gift.service.MemberService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import jakarta.servlet.http.HttpServletRequest;


@Component
public class LoginMemberArgumentResolver implements HandlerMethodArgumentResolver {

    private final MemberService memberService;
    private final JwtUtil jwtUtil;

    private static final Logger logger = LoggerFactory.getLogger(LoginMemberArgumentResolver.class);


    public LoginMemberArgumentResolver(MemberService memberService, JwtUtil jwtUtil) {
        this.memberService = memberService;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(LoginMember.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();
        String authHeader = request.getHeader("Authorization");
        logger.info("헤더 확인: " + authHeader);

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);

            if (jwtUtil.validateToken(token)) {
                String email = jwtUtil.getEmailFromToken(token);
                Member member = memberService.findByEmail(email);

                if (member != null) {
                    return member;
                } else {
                    throw new IllegalStateException("Authenticated member not found in the database.");
                }
            }
        }
        throw new IllegalStateException("Invalid or missing JWT token");
    }
}

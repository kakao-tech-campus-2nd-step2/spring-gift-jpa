package gift.Login.auth;

import gift.Login.exception.UserNotFoundException;
import gift.Login.service.MemberService;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import gift.Login.model.Member;

@Component
public class LoginMemberArgumentResolver implements HandlerMethodArgumentResolver {

    private final JwtUtil jwtUtil;
    private final MemberService memberService;

    public LoginMemberArgumentResolver(JwtUtil jwtUtil, MemberService memberService) {
        this.jwtUtil = jwtUtil;
        this.memberService = memberService;
    }

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(LoginMember.class) && parameter.getParameterType().equals(Member.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        String header = webRequest.getHeader("Authorization");
        if (header == null || !header.startsWith("Bearer ")) {
            throw new UserNotFoundException("Missing or invalid Authorization header");
        }

        String token = header.substring(7); // "Bearer " 접두사 제거
        if (!jwtUtil.isTokenValid(token)) {
            throw new UserNotFoundException("Invalid JWT token");
        }

        String email = jwtUtil.extractEmail(token);
        Member member = memberService.findMemberByEmail(email);
        if (member == null) {
            throw new UserNotFoundException("User not found with email: " + email);
        }
        return member;
    }
}

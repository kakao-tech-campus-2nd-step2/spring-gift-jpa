package gift.Login.auth;

import gift.Login.exception.UserNotFoundException;
import gift.Login.repository.MemberRepository;
import gift.Login.service.MemberService;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import gift.Login.model.Member;

import java.util.Optional;

@Component
public class LoginMemberArgumentResolver implements HandlerMethodArgumentResolver {

    private final JwtUtil jwtUtil;
    private final MemberRepository memberRepository;

    public LoginMemberArgumentResolver(JwtUtil jwtUtil, MemberRepository memberRepository) {
        this.jwtUtil = jwtUtil;
        this.memberRepository = memberRepository;
    }

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(LoginMember.class) && parameter.getParameterType().equals(Member.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        // header 검증
        String header = webRequest.getHeader("Authorization");
        if (header == null || !header.startsWith("Bearer ")) {
            throw new UserNotFoundException("Missing or invalid Authorization header");
        }

        // JWT 토큰 검증
        String token = header.substring(7); // "Bearer " 접두사 제거
        if (!jwtUtil.isTokenValid(token)) {
            throw new UserNotFoundException("Invalid JWT token");
        }

        // 토큰에서 이메일 추출
        String email = jwtUtil.extractEmail(token);
        Optional<Member> member = memberRepository.findByEmail(email);
        if (member.isEmpty()) {
            throw new UserNotFoundException("User not found with email: " + email);
        }
        return member;
    }
}

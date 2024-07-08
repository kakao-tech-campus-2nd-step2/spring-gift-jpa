package gift.config;

import gift.jwt.JwtUtil;
import gift.service.MemberService;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    private LoginMemberArgumentResolver loginMemberArgumentResolver;
    private final MemberService memberService;
    private final JwtUtil jwtUtil;

    private final AuthorizationHeader authorizationHeader;

    public WebConfig(MemberService memberService, JwtUtil jwtUtil,LoginMemberArgumentResolver loginMemberArgumentResolver,AuthorizationHeader authorizationHeader) {
        this.memberService = memberService;
        this.jwtUtil = jwtUtil;
        this.loginMemberArgumentResolver = loginMemberArgumentResolver;
        this.authorizationHeader = authorizationHeader;
    }
    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new LoginMemberArgumentResolver(memberService, jwtUtil,authorizationHeader));
    }
}
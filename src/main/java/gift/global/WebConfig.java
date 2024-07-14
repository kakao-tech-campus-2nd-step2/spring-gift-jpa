package gift.global;

import gift.domain.annotation.ValidAdminMemberArgumentResolver;
import gift.domain.annotation.ValidMemberArgumentResolver;
import gift.domain.service.MemberService;
import gift.global.util.JwtUtil;
import java.util.List;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final MemberService memberService;
    private final JwtUtil jwtUtil;

    public WebConfig(MemberService memberService, JwtUtil jwtUtil) {
        this.memberService = memberService;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new ValidMemberArgumentResolver(memberService, jwtUtil));
        resolvers.add(new ValidAdminMemberArgumentResolver(memberService, jwtUtil));
    }
}

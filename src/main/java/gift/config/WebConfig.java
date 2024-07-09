package gift.config;

import gift.interceptor.JwtInterceptor;
import gift.resolver.LoginUserArgumentResolver;
import gift.service.JwtUtil;
import gift.service.MemberService;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    private  final MemberService memberService;
    private final JwtInterceptor jwtInterceptor;
    private final JwtUtil jwtUtil;

    public WebConfig(MemberService memberService, JwtInterceptor jwtInterceptor, JwtUtil jwtUtil) {
        this.memberService = memberService;
        this.jwtInterceptor = jwtInterceptor;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(jwtInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns("/members/register", "/members/login");
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new LoginUserArgumentResolver(memberService, jwtUtil));
    }
}

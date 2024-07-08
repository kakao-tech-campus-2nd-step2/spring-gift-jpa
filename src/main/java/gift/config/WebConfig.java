package gift.config;

import gift.filter.TokenInterceptor;
import gift.resolver.LoginMemberArgumentResolver;
import gift.service.TokenService;
import gift.service.UserService;
import gift.util.AuthorizationHeaderProcessor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final TokenInterceptor tokenInterceptor;
    private final UserService userService;
    private final TokenService tokenService;
    private final AuthorizationHeaderProcessor authorizationHeaderProcessor;

    public WebConfig(TokenInterceptor tokenInterceptor, UserService userService, TokenService tokenService, AuthorizationHeaderProcessor authorizationHeaderProcessor) {
        this.tokenInterceptor = tokenInterceptor;
        this.userService = userService;
        this.tokenService = tokenService;
        this.authorizationHeaderProcessor = authorizationHeaderProcessor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(tokenInterceptor)
                .addPathPatterns("/api/wishes")  // 토큰 검증이 필요한 경로 설정
                .excludePathPatterns("/api/users/login", "/api/users/register"); // 로그인, 회원가입은 제외
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new LoginMemberArgumentResolver(userService, tokenService, authorizationHeaderProcessor));
    }
}

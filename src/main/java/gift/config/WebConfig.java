package gift.config;

import gift.resolver.LoginUserIdArgumentResolver;
import gift.service.UserService;
import gift.util.auth.JwtUtil;
import java.util.List;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final JwtUtil jwtUtil;
    private final UserService userService;
    private final LoginUserIdArgumentResolver loginUserIdArgumentResolver;

    public WebConfig(JwtUtil jwtUtil, UserService userService, LoginUserIdArgumentResolver loginUserIdArgumentResolver) {
        this.jwtUtil = jwtUtil;
        this.userService = userService;
        this.loginUserIdArgumentResolver = loginUserIdArgumentResolver;
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(loginUserIdArgumentResolver);
    }
}

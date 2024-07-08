package gift.config;

import gift.authorization.JwtUtil;
import gift.service.LoginMemberArgumentResolver;
import gift.service.WishService;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    private final WishService wishService;
    private final JwtUtil jwtUtil;

    public WebMvcConfig(WishService wishService, JwtUtil jwtUtil) {
        this.wishService = wishService;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new LoginMemberArgumentResolver(wishService, jwtUtil));
    }
}

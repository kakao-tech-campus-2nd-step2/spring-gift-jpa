package gift.global;

import gift.domain.annotation.ValidAdminUserArgumentResolver;
import gift.domain.service.UserService;
import gift.domain.annotation.ValidUserArgumentResolver;
import gift.global.util.JwtUtil;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final UserService userService;
    private final JwtUtil jwtUtil;

    @Autowired
    public WebConfig(UserService userService, JwtUtil jwtUtil) {
        this.userService = userService;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new ValidUserArgumentResolver(userService, jwtUtil));
        resolvers.add(new ValidAdminUserArgumentResolver(userService, jwtUtil));
    }
}

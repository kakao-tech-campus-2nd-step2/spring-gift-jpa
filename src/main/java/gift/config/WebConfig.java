package gift.config;

import java.util.List;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final TokenInterceptor tokenInterceptor;
    private final LoginUserArgumentResolver loginUserArgumentResolver;

    public WebConfig(TokenInterceptor tokenInterceptor,
        LoginUserArgumentResolver loginUserArgumentResolver) {
        this.tokenInterceptor = tokenInterceptor;
        this.loginUserArgumentResolver = loginUserArgumentResolver;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(tokenInterceptor)
            .addPathPatterns("/**")
            .excludePathPatterns("/users/**");
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(loginUserArgumentResolver);
    }
}

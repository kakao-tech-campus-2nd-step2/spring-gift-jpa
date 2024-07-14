package gift.config;

import gift.interceptor.AuthInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

    private final AuthInterceptor authInterceptor;

    public InterceptorConfig(AuthInterceptor loginInterceptor) {
        this.authInterceptor = loginInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authInterceptor)
            .order(1)
            .addPathPatterns("/**")
            .excludePathPatterns("/login", "/register", "/move-sign-up", "/move-login")
        ;
    }
}
package gift.product.config;

import gift.product.JwtCookieToHeaderInterceptor;
import gift.product.TokenValidationInterceptor;
import gift.product.service.AuthService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final AuthService authService;

    public WebConfig(AuthService authService) {
        this.authService = authService;
    }

    @Bean
    public TokenValidationInterceptor tokenValidationInterceptor() {
        return new TokenValidationInterceptor(authService);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(tokenValidationInterceptor())
            .order(2)
            .addPathPatterns("/admin/wishes")
            .addPathPatterns("/admin/wishes/**")
            .addPathPatterns("/api/wishes")
            .addPathPatterns("/api/wishes/**");

        registry.addInterceptor(new JwtCookieToHeaderInterceptor())
            .order(1)
            .addPathPatterns("/admin/login")
            .addPathPatterns("/admin/login/**")
            .addPathPatterns("/admin/wishes")
            .addPathPatterns("/admin/wishes/**");
    }
}

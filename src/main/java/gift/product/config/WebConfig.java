package gift.product.config;

import gift.product.JwtCookieToHeaderInterceptor;
import gift.product.TokenValidationInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Bean
    public TokenValidationInterceptor tokenValidationInterceptor() {
        return new TokenValidationInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(tokenValidationInterceptor())
            .order(2)
            .addPathPatterns("/api/**")
            .addPathPatterns("/admin/**")
            .excludePathPatterns("/admin/login/")
            .excludePathPatterns("/admin/login/**");

        registry.addInterceptor(new JwtCookieToHeaderInterceptor())
            .order(1)
            .addPathPatterns("/admin/**")
            .excludePathPatterns("/admin/login")
            .excludePathPatterns("/admin/login/**");
    }
}

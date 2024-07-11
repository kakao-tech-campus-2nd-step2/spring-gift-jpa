package gift.auth;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    public static final String ALLOWED_METHOD_NAMES = "GET,HEAD,POST,PUT,DELETE,TRACE,OPTIONS,PATCH";

    private JwtInterceptor jwtInterceptor;

    public WebConfig(JwtInterceptor jwtInterceptor) {
        this.jwtInterceptor = jwtInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(jwtInterceptor)
            .addPathPatterns("/**") // 모든 요청에 대해 인터셉터를 적용
            .excludePathPatterns("/members/register", "/members/login"); // 로그인 및 회원가입 요청은 제외
    }

    @Override
    public void addCorsMappings(final CorsRegistry registry) {
        registry.addMapping("/**")
            .allowedMethods(ALLOWED_METHOD_NAMES.split(","))
            .exposedHeaders(HttpHeaders.LOCATION);
    }
}

package gift.main.config;

import gift.main.interceptor.AuthLoginInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;

@Configuration
@EnableWebMvc
public class WebConfig {

    private final AuthLoginInterceptor authLoginInterceptor;


    public WebConfig(AuthLoginInterceptor authLoginInterceptor) {
        this.authLoginInterceptor = authLoginInterceptor;
    }

    public void addInterceptors(InterceptorRegistry registry) {
        // 인터셉터를 등록하는 메서드/members/register
        // CustomInterceptor를 등록하고, 모든 URL에 대해 인터셉터를 적용하도록 설정
        registry.addInterceptor(authLoginInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns("*/members/login")
                .excludePathPatterns("*/members/register");
    }
}

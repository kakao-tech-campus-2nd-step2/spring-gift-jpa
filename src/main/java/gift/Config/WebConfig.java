package gift.Config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final InterceptorOfToken interceptorOfToken;

    public WebConfig(InterceptorOfToken interceptorOfToken) {
        this.interceptorOfToken = interceptorOfToken;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(interceptorOfToken)
                .addPathPatterns("/**") // 인터셉터가 적용될 경로 패턴을 설정
                .excludePathPatterns("/user/**"); // 인터셉처 적용 안 해도 되는 경로 패턴 설정
    }
}
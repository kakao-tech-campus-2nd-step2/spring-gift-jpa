package gift.config;

import gift.authService.Interceptor;
import gift.authService.LoginUserArgumentResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * WebConfig 클래스는 웹 애플리케이션에 대한 설정을 구성합니다. 여기에는 요청 및 응답을 처리할 인터셉터와, 어노테이션 기반 인자 해결기를 등록하는 작업이 포함됩니다.
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private Interceptor interceptor;

    @Autowired
    private LoginUserArgumentResolver loginUserArgumentResolver;

    /**
     * 커스텀 인터셉터를 애플리케이션의 인터셉터 레지스트리에 추가합니다. 이 인터셉터는 "/api/login", "/api/login/signup"을 제외한 모든 요청에 대해
     * 실행됩니다.
     *
     * @param registry 인터셉터가 추가될 InterceptorRegistry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(interceptor).addPathPatterns("/**")
            .excludePathPatterns("/api/login", "/api/login/signup");
    }

    /**
     * 커스텀 Argument Resolver를 애플리케이션에 추가합니다.
     *
     * @param resolvers 커스텀 Argument Resolver가 추가될 리스트
     */
    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(loginUserArgumentResolver);
    }
}

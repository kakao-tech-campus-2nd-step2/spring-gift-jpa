package gift.global.configuration;

import gift.global.component.LoginInterceptor;
import gift.global.component.ProductsResolver;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

// WebMvcConfigurer를 구현하는 클래스.
@Configuration
@SpringBootConfiguration
public class LoginConfigurer implements WebMvcConfigurer {

    private final LoginInterceptor loginInterceptor;
    private final ProductsResolver productsResolver;

    @Autowired
    public LoginConfigurer(LoginInterceptor loginInterceptor,
        ProductsResolver productsResolver) {
        this.loginInterceptor = loginInterceptor;
        this.productsResolver = productsResolver;
    }

    // 인터셉터를 추가하는 메서드를 재정의하여 loginInterceptor를 등록하도록 함.
    @Override
    public void addInterceptors(InterceptorRegistry interceptorRegistry) {
        interceptorRegistry
            // 인터셉터 추가
            .addInterceptor(loginInterceptor)
            // 아직은 하나 뿐이지만 순번을 정했습니다.
            .order(1)
            // 가능한 경로 (api와 view를 반환하는 경로 모두 추가)
            .addPathPatterns("/user/**")
            .addPathPatterns("/api/products/user")
            .addPathPatterns("/api/wishlist/**");
    }

    // argument resolver를 추가하는 메서드 재정의
    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(productsResolver);
    }
}

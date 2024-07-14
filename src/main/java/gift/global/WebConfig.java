package gift.global;

import gift.global.auth.interceptor.AuthenticationInterceptor;
import gift.global.auth.interceptor.AuthorizationInterceptor;
import gift.global.auth.resolver.LoginInfoArgumentResolver;
import gift.global.converter.StringToSearchTypeConverter;
import java.util.List;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final AuthorizationInterceptor authorizationInterceptor;
    private final AuthenticationInterceptor authenticationInterceptor;
    private final StringToSearchTypeConverter stringToSearchTypeConverter;

    public WebConfig(
        AuthorizationInterceptor authorizationInterceptor,
        AuthenticationInterceptor authenticationInterceptor,
        StringToSearchTypeConverter stringToSearchTypeConverter
    ) {
        this.authorizationInterceptor = authorizationInterceptor;
        this.authenticationInterceptor = authenticationInterceptor;
        this.stringToSearchTypeConverter = stringToSearchTypeConverter;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //인증 인터셉터
        registry.addInterceptor(authenticationInterceptor).order(1);
        //인가 인터셉터
        registry.addInterceptor(authorizationInterceptor).order(2);
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new LoginInfoArgumentResolver());
    }

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(stringToSearchTypeConverter);
    }
}

package gift.config;

import gift.resolver.LoginUserHandlerMethodArgumentResolver;
import java.util.List;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final LoginUserHandlerMethodArgumentResolver loginUserHandlerMethodArgumentResolver;

    public WebConfig(LoginUserHandlerMethodArgumentResolver loginUserHandlerMethodArgumentResolver){
        this.loginUserHandlerMethodArgumentResolver=loginUserHandlerMethodArgumentResolver;
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers){
        resolvers.add(loginUserHandlerMethodArgumentResolver);
    }

}

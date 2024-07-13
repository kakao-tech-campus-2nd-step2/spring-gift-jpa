package gift.config;

import gift.authentication.filter.AuthenticationExceptionHandlerFilter;
import gift.authentication.filter.AuthenticationFilter;
import gift.web.resolver.LoginMemberArgumentResolver;
import gift.authentication.token.JwtResolver;
import java.util.List;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    private final LoginMemberArgumentResolver loginUserArgumentResolver;
    private final JwtResolver jwtResolver;

    public WebConfig(LoginMemberArgumentResolver loginUserArgumentResolver, JwtResolver jwtResolver) {
        this.loginUserArgumentResolver = loginUserArgumentResolver;
        this.jwtResolver = jwtResolver;
    }

    @Bean
    public FilterRegistrationBean authenticationExceptionHandlerFilter() {
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean(new AuthenticationExceptionHandlerFilter());
        filterRegistrationBean.setOrder(1);
        return filterRegistrationBean;
    }

    @Bean
    public FilterRegistrationBean authenticationFilter() {
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean(new AuthenticationFilter(jwtResolver));
        filterRegistrationBean.setOrder(2);
        return filterRegistrationBean;
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(loginUserArgumentResolver);
    }

}
